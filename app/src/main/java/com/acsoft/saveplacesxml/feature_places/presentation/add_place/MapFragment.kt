package com.acsoft.saveplacesxml.feature_places.presentation.add_place

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.acsoft.saveplacesxml.R
import com.acsoft.saveplacesxml.databinding.FragmentMapBinding
import com.acsoft.saveplacesxml.feature_places.domain.model.Place
import com.acsoft.saveplacesxml.util.LocationUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class MapFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    companion object {
        const val PERMISSION_LOCATION_REQUEST_CODE = 1
    }

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    private var latitude = 0.0
    private var longitude = 0.0
    private var map:GoogleMap? = null
    private val addPlaceViewModel : AddPlaceViewModel by viewModels()
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        getCurrentLocation(map)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        requestLocationPermission()
        binding.requestPermissionButton.setOnClickListener {
            requestLocationPermission()
        }

        binding.buttonContinue.setOnClickListener {
            if (validateForm()) {
                binding.buttonContinue.isClickable = false
                val place = Place(
                    title = binding.etTitle.text.toString(),
                    description = binding.etDescription.text.toString(),
                    latitude = latitude,
                    longitude = longitude
                )
                addPlaceViewModel.registerPlace(place)
                findNavController().navigate(R.id.action_mapFragment_to_FirstFragment)
                Toast.makeText(requireContext(),getString(R.string.place_save_success),Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(),getString(R.string.complete_form_to_continue),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm() : Boolean {
        val title = binding.etTitle.text
        val description = binding.etDescription.text
        return (latitude!=0.0 && longitude!=0.0 && !title.isNullOrEmpty() && !description.isNullOrEmpty())
    }

    private fun setViewVisibility() {
        if (hasLocationPermission()) {
            requestPermissionLayoutInvisible()
        } else {
            requestPermissionLayoutVisible()
        }
    }


    private fun hasLocationPermission() : Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            "esta aplicación no puede funcionar correctamente sin el permiso de localización",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun requestPermissionLayoutVisible() {
        binding.requestPermissionLayout.visibility = View.VISIBLE
    }

    private fun requestPermissionLayoutInvisible() {
        binding.requestPermissionLayout.visibility = View.GONE
    }

    private fun getCurrentLocation(googleMap: GoogleMap?) {
        if(hasLocationPermission()) {
           if(LocationUtils.isGpsEnabled(requireContext())) {
               //get latitude and longitude
               fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                   if (task.result != null) {
                       latitude = task.result.latitude
                       longitude = task.result.longitude
                       val myLocation = LatLng(latitude,longitude)
                       googleMap?.addMarker(MarkerOptions()
                           .position(myLocation)
                           .title(getString(R.string.my_location)))
                       googleMap?.moveCamera(CameraUpdateFactory.newLatLng(myLocation))
                       binding.registerPlaceLayout.visibility = View.VISIBLE
                   }
               }
           } else {
               val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
               startActivity(intent)
           }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        //empty
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestLocationPermission()
        }
    }

    override fun onResume() {
        super.onResume()
        setViewVisibility()
        getCurrentLocation(map)
    }
}