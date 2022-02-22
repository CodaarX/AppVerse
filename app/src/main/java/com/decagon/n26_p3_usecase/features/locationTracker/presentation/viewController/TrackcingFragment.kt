package com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewController

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.utils.GenericDialogueBuilder
import com.decagon.n26_p3_usecase.commons.utils.hideView
import com.decagon.n26_p3_usecase.commons.utils.showView
import com.decagon.n26_p3_usecase.core.presentation.MainActivity
import com.decagon.n26_p3_usecase.core.baseClasses.BaseFragment
import com.decagon.n26_p3_usecase.databinding.FragmentTrackcingBinding
import com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewModel.MainViewModel
import com.decagon.n26_p3_usecase.features.locationTracker.services.PolyLine
import com.decagon.n26_p3_usecase.features.locationTracker.services.TrackingService
import com.decagon.n26_p3_usecase.features.locationTracker.utils.LocationProvider
import com.decagon.n26_p3_usecase.features.locationTracker.utils.TrackingUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackcingFragment : BaseFragment() {

    private val  viewModel: MainViewModel by viewModels()
    private var isTracking = false
    private var pathPoints = mutableListOf<PolyLine>()
    private var map: GoogleMap? = null
    private lateinit var  marker : MarkerOptions
    private var mark : Marker? = null
    private var currentTimeMills = 0L
    var buttonText = "start"

    private lateinit var binding : FragmentTrackcingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocationProvider.provide(requireContext(), requireActivity() as MainActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTrackcingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnToggleRun.setOnClickListener { toggleRun() }
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync { it ->
            map = it
            addAllPolylines()
        }
        subscribeToObservers()
        binding.cancelLayout?.setOnClickListener {
            showTrackingCancelDialogue()
            toggleRun()
        }
    }

    private fun addAllPolylines(){
        pathPoints.forEach {
            val polylineOptions = PolylineOptions()
                .color(TrackingUtils.POLY_LINE_COLOR)
                .width(TrackingUtils.POLY_LINE_WIDTH)
                .addAll(it)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun subscribeToObservers(){
        TrackingService.isTracking.observe(viewLifecycleOwner) { updateTracking(it) }
        TrackingService.pathPoints.observe(viewLifecycleOwner) {
            pathPoints = it
            addLatestPolyLine()
            moveCameraToUser()
        }
        TrackingService.timeRunInMillis.observe(viewLifecycleOwner) {
            currentTimeMills = it
            val formattedTime = TrackingUtils.getFormattedStopWatchTime(currentTimeMills, true)
            binding.tvTimer.text = formattedTime

            if(currentTimeMills > 0L){
                binding.mapBottomBar!!.showView()
            } else {
                binding.mapBottomBar!!.hideView()
            }
        }

    }

    private fun toggleRun(){
        isTracking = if(isTracking){
            sendCommandToService(TrackingUtils.ACTION_PAUSE_SERVICE)
            false
        } else {
            sendCommandToService(TrackingUtils.ACTION_START_OR_RESUME_SERVICE)
            true
        }
    }

    private fun updateTracking(isTracking: Boolean){
        if(!isTracking){
            binding.btnToggleRun.text = buttonText
        } else {
            binding.btnToggleRun.text = "Pause"
            buttonText = "Resume"
        }
    }

    private fun moveCameraToUser(){
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()){
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(
                pathPoints.last().last(),
                TrackingUtils.MAP_ZOOM
            ))
            addMarker()
        }
    }

    private fun showTrackingCancelDialogue(){
        GenericDialogueBuilder.showDialogue(
            requireContext(),
            R.style.AlertDialogTheme,
            "Cancel Run",
            "Are you sure you want to cancel the current run?",
            R.drawable.ic_delete,
            "Yes", "No",
            { cancelRunDialoguePositiveClick() }, { toggleRun() })
     }

    private fun cancelRunDialoguePositiveClick(){
        sendCommandToService(TrackingUtils.ACTION_STOP_SERVICE)
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun addMarker(){
        TrackingService.userCurrentLocation.observe(viewLifecycleOwner) {
            marker = MarkerOptions()
                .position(it)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            mark?.remove()
            mark = map?.addMarker(marker)!!
        }
    }

    private fun addLatestPolyLine(){ // add the latest polyline to the map
        if(pathPoints.isNotEmpty() && pathPoints.last().size > 1){
            val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLng = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(TrackingUtils.POLY_LINE_COLOR)
                .width(TrackingUtils.POLY_LINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun sendCommandToService(command: String) { // send command to service
        Intent(this.context, TrackingService::class.java).apply {
            this.action = command
            this@TrackcingFragment.context?.startService(this)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        binding.mapView.onDestroy()
//    }
}