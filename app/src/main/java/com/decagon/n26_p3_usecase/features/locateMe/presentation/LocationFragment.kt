package com.decagon.n26_p3_usecase.features.locateMe.presentation

import `in`.myinnos.savebitmapandsharelib.SaveAndShare
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.utils.NetworkLiveData
import com.decagon.n26_p3_usecase.commons.utils.Tools
import com.decagon.n26_p3_usecase.commons.utils.toast
import com.decagon.n26_p3_usecase.core.presentation.MainActivity
import com.decagon.n26_p3_usecase.databinding.FragmentLocationBinding
import com.decagon.n26_p3_usecase.features.locateMe.utils.MapTools
import com.decagon.n26_p3_usecase.features.locationTracker.utils.LocationProvider
import com.decagon.n26_p3_usecase.features.locationTracker.utils.TrackingUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: FragmentLocationBinding
    private lateinit var mMap: GoogleMap

    private val callback = OnMapReadyCallback { gMap ->
        val configuredmap = MapTools.configActivityMaps(gMap)
        configuredmap?.let { mMap = it }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocationProvider.provide(requireContext(), requireActivity() as MainActivity)
        fusedLocationProviderClient = FusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLocationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tools.setSystemBarColor(requireActivity(), R.color.colorPrimary)
        (activity as MainActivity).supportActionBar?.title = "My Location"
        setClickListeners()
        buildMap()
    }

    private fun setClickListeners(){
        binding.listButton.setOnClickListener {
            Toast.makeText(requireContext(), "Map Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.locateMeButton.setOnClickListener {
            NetworkLiveData.observe(viewLifecycleOwner) {
                if (it) {
                    updateLocationTracking(true)
                } else {
                    toast(requireContext(), "No Internet Connection")
                }
            }
        }

        binding.directionButton.setOnClickListener {
            Toast.makeText(requireContext(), "Add Clicked", Toast.LENGTH_SHORT)
                .show()
        }

        binding.shareButton.setOnClickListener {
            shareLocationSnapShot()
        }
    }

    private fun shareLocationSnapShot(){
        mMap.snapshot { bitmap ->
            SaveAndShare.save(requireActivity(), bitmap, "run_snapshot","my current location", "image/jpeg")
        }
    }

    private fun buildMap() {
        (childFragmentManager
            .findFragmentById(R.id.my_location_map) as? SupportMapFragment)?.
            getMapAsync(callback)
            updateLocationTracking(true)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun updateLocationTracking(isTracking: Boolean){
        if(isTracking){
            if(TrackingUtils.hasLocationPermission(requireContext())){
                val request = LocationRequest().apply {
                    interval = TrackingUtils.LOCATION_UPDATE_INTERVAL // location update will be triggered approximately every specified interval
                    fastestInterval = TrackingUtils.FASTEST_LOCATION_UPDATE_INTERVAL // location updates will be received faster than this interval
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                fusedLocationProviderClient.requestLocationUpdates(request, locationCallback,  Looper.getMainLooper()) // request location updates
            } else {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback) // remove location
            }
        }
    }

    private fun zoomingLocation(location: LatLng): CameraUpdate {
        return CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 15.5f)
    }

    private val locationCallback = object : LocationCallback(){ //callback for location updates

        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result) // get location from callback

            val mLastLocation: Location = result.lastLocation

            val nowLocation = LatLng(
                mLastLocation.latitude,
                mLastLocation.longitude
            )

            if (::mMap.isInitialized) {
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(nowLocation).title("You are here"))
                mMap.animateCamera(zoomingLocation(nowLocation))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        updateLocationTracking(false)
    }

}