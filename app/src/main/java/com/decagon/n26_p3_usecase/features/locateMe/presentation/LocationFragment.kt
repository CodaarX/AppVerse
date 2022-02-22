package com.decagon.n26_p3_usecase.features.locateMe.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.utils.Tools
import com.decagon.n26_p3_usecase.core.presentation.MainActivity
import com.decagon.n26_p3_usecase.databinding.FragmentLocationBinding
import com.decagon.n26_p3_usecase.features.locateMe.utils.MapTools
import com.decagon.n26_p3_usecase.features.locationTracker.utils.LocationProvider
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocationProvider.provide(requireContext(), requireActivity() as MainActivity)
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
        initMapFragment()
        Tools.setSystemBarColor(requireActivity(), R.color.colorPrimary)
        setClickListeners()
    }

    private fun initMapFragment() {
        val mapFragment = binding.myLocationMap
        mapFragment.getMapAsync { googleMap ->
            mMap = MapTools.configActivityMaps(googleMap)!!
            val markerOptions = MarkerOptions().position(LatLng(37.7610237, -122.4217785))
            mMap.addMarker(markerOptions)
            mMap.moveCamera(zoomingLocation()!!)
            mMap.setOnMarkerClickListener {
                try {
                    mMap.animateCamera(zoomingLocation()!!)
                } catch (e: Exception) {
                }
                true
            }
        }
    }

    private fun zoomingLocation(): CameraUpdate? {
        return CameraUpdateFactory.newLatLngZoom(LatLng(37.76496792, -122.42206407), 13f)
    }

    private fun setClickListeners(){
        binding.listButton.setOnClickListener {
            Toast.makeText(requireContext(), "Map Clicked", Toast.LENGTH_SHORT)
                .show()
        }

        binding.addButton.setOnClickListener {
            if (::mMap.isInitialized) {
                mMap?.clear()
                val latLng = LatLng(
                    LocationProvider.latitude,
                    LocationProvider.longitude
                )
                mMap?.addMarker(MarkerOptions().position(latLng).title("You are here"))
                mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            }
        }

        binding.mapButton.setOnClickListener {
            Toast.makeText(requireContext(), "Add Clicked", Toast.LENGTH_SHORT)
                .show()
        }
    }

}