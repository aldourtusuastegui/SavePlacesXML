package com.acsoft.saveplacesxml.feature_places.presentation.add_place

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.acsoft.saveplacesxml.R
import com.acsoft.saveplacesxml.databinding.FragmentMapBinding
import com.acsoft.saveplacesxml.util.UserPermissions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MapFragment : Fragment(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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
        requestPermission()

        binding.requestPermissionButton.setOnClickListener {
            requestPermission()
        }
    }

    private fun requestPermission() {
        if(!UserPermissions.hasLocationPermissions(requireContext())) {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                EasyPermissions.requestPermissions(
                    this,
                    "¿Quieres otorgar este permiso de localización?",
                    0,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "¿Quieres otorgar este permiso?",
                    0,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
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
        Log.d("TAG","permiso concedido")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog
                .Builder(this)
                .setTitle(R.string.permission_required)
                .setRationale(R.string.permission_required_rationale)
                .setPositiveButton(R.string.accept)
                .setNegativeButton(R.string.cancel)
                .build().show()
        } else {
            requestPermissionLayoutInvisible()
        }
    }

    private fun requestPermissionLayoutVisible() {
        binding.requestPermissionLayout.visibility = View.VISIBLE
    }

    private fun requestPermissionLayoutInvisible() {
        binding.requestPermissionLayout.visibility = View.GONE
    }

    override fun onRationaleAccepted(requestCode: Int) {
       requestPermissionLayoutInvisible()
       Log.d("TAG","Acepted")
    }

    override fun onRationaleDenied(requestCode: Int) {
        if (!UserPermissions.hasLocationPermissions(requireContext())) {
           /* if (requireParentFragment().isResumed) {
                requestPermissionLayoutInvisible()
            } else {
                requestPermissionLayoutVisible()
            }*/
            requestPermissionLayoutVisible()
            Log.d("TAG","denegado")
        } else {
            Log.d("TAG","Tiene permisos parciales")
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG","Se regreso a esta pantall")
        Log.d("TAG","permiso : ${UserPermissions.hasLocationPermissions(requireContext())}")
    }
}