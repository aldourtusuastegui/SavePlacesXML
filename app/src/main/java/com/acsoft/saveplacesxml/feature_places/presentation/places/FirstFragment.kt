package com.acsoft.saveplacesxml.feature_places.presentation.places

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acsoft.saveplacesxml.R
import com.acsoft.saveplacesxml.databinding.FragmentFirstBinding
import com.acsoft.saveplacesxml.services.TimerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val placesViewModel : PlacesViewModel by viewModels()
    private lateinit var adapter: PlacesListAdapter
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PlacesListAdapter()
        serviceIntent = Intent(context,TimerService::class.java)
        context?.registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPlacesList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlacesList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvPlacesList.adapter = adapter
        lifecycleScope.launch {
            placesViewModel.getPlacesList().collect { placeList ->
                placeList.let {
                    adapter.setPlaceList(placeList)
                    placesViewModel.workManagerExample(placeList)
                }
            }
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_mapFragment)
        }

        binding.btnStart.setOnClickListener {
            startStopTimer()
        }

        binding.btnReset.setOnClickListener {
            resetTimer()
        }
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA,0.0)
            binding.tvTime.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double) : String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 84600 / 3600
        val minutes = resultInt %86400 % 3600 / 60
        val seconds = resultInt %86400 %3600 % 60
        return makeTimeString(hours,minutes,seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec:Int): String = String.format("%02d:%02d:%02d",hour,min,sec)

    private fun resetTimer() {
        stopTimer()
        time = 0.0
        binding.tvTime.text = getTimeStringFromDouble(time)
    }

    private fun startStopTimer() {
        if (timerStarted) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        context?.startService(serviceIntent)
        binding.btnStart.text = "Detener"
        timerStarted = true
    }

    private fun stopTimer() {
        context?.stopService(serviceIntent)
        binding.btnStart.text = "Iniciar"
        timerStarted = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}