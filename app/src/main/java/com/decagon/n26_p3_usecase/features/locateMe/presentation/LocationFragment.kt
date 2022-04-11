package com.decagon.n26_p3_usecase.features.locateMe.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.ui.showView
import com.decagon.n26_p3_usecase.commons.ui.toast
import com.decagon.n26_p3_usecase.commons.utils.*
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
import com.maps.route.extensions.drawRouteOnMap
import com.maps.route.extensions.moveCameraOnMap
import dagger.hilt.android.AndroidEntryPoint
import `in`.myinnos.savebitmapandsharelib.SaveAndShare
import java.io.IOException

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: FragmentLocationBinding
    private lateinit var mMap: GoogleMap
    var showingRoute = false

    //  private lateinit var placesClient : PlacesClient

    private val callback = OnMapReadyCallback { gMap ->
        val configuredmap = MapTools.configActivityMaps(gMap)
        configuredmap?.let { mMap = it }
    }

    @SuppressLint("VisibleForTests")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocationProvider.provide(requireContext(), requireActivity() as MainActivity)
        fusedLocationProviderClient = FusedLocationProviderClient(requireActivity())
//        Places.initialize(requireContext(), getString(R.string.google_maps_key))
//        placesClient = Places.createClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLocationBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tools.setSystemBarColor(requireActivity(), R.color.colorPrimary)
//        Tools.setSystemBarLight(requireActivity())
        setTitle("My Location")
//        initializePlaces()

        setClickListeners()
        buildMap()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setClickListeners() {
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

            binding.locationDestination.showView()
            binding.locationSearchBoxButton.setOnClickListener {
                NetworkLiveData.observe(viewLifecycleOwner) {
                    if (it) {
                        val destination = binding.textViewDestination.text.toString()
                        if (destination.isNotEmpty()) {
                            drawDirection(destination)
                        } else { toast(requireContext(), "Please enter a valid destination") }
                    } else { toast(requireContext(), "No Internet Connection") }
                }
            }
            NetworkLiveData.removeObservers(viewLifecycleOwner)
        }

        binding.shareButton.setOnClickListener { shareLocationSnapShot() }
    }

//    private fun initializePlaces(){
//        val autoCompleteSupportMapFragment = (AutocompleteSupportFragment())
//        childFragmentManager.findFragmentById(R.id.my_location_map)
//        autoCompleteSupportMapFragment.apply {
//            setTypeFilter(TypeFilter.ADDRESS)
//            setLocationBias(
//                RectangularBounds.newInstance(
//                    LatLng(2.69170169436 ,14.5771777686, ),
//                    LatLng(13.8659239771, 4.24059418377)
//                )
//            )
//            setCountries("NG") // set the country
//            setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
//        }
//
//        object : PlaceSelectionListener {
//            override fun onPlaceSelected(p0: Place) {
//                log("Place Selected ${p0.name}")
//               // binding.textViewDestination.setText(p0.name)
//                p0.name?.let {
//                    drawDirection(it)
//                }
//            }
//
//            override fun onError(p0: Status) {
//                log("Error ${p0.statusMessage}")
//            }
//        }
//    }

    private fun shareLocationSnapShot() {
        mMap.snapshot { bitmap ->
            SaveAndShare.save(requireActivity(), bitmap, "run_snapshot", "my current location", "image/jpeg")
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun buildMap() {
        (
            childFragmentManager
                .findFragmentById(R.id.my_location_map) as? SupportMapFragment
            )
            ?.getMapAsync(callback)
        updateLocationTracking(true)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun updateLocationTracking(isTracking: Boolean) {
        if (isTracking) {
            if (TrackingUtils.hasLocationPermission(requireContext())) {
                val request = LocationRequest().apply {
                    interval = TrackingUtils.LOCATION_UPDATE_INTERVAL // location update will be triggered approximately every specified interval
                    fastestInterval = TrackingUtils.FASTEST_LOCATION_UPDATE_INTERVAL // location updates will be received faster than this interval
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                fusedLocationProviderClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper()) // request location updates
            } else {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback) // remove location
            }
        }
    }

    private fun zoomingLocation(location: LatLng): CameraUpdate {
        return CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 15.5f)
    }

    private val locationCallback = object : LocationCallback() { // callback for location updates

        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result) // get location from callback

            val mLastLocation: Location = result.lastLocation

            if (!showingRoute) {
                val nowLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)

                if (::mMap.isInitialized) {
                    mMap.clear() // -=-----------------------------
                    mMap.addMarker(MarkerOptions().position(nowLocation).title("You are here"))
                    mMap.animateCamera(zoomingLocation(nowLocation))
                }
            }
        }
    }

    private fun drawDirection(location: String) {

        showingRoute = true
        val origin = mMap.cameraPosition.target
        val destinationCoordinates = getDestination(location)

        val source = LatLng(origin.latitude, origin.longitude) // starting point (LatLng)
        val destination = LatLng(destinationCoordinates.latitude, destinationCoordinates.longitude) // ending point (LatLng)

        if (showingRoute) {

            mMap.run {

                moveCameraOnMap(latLng = source) // if you want to zoom the map to any point

                // Called the drawRouteOnMap extension to draw the polyline/route on google maps
                drawRouteOnMap(
                    getString(R.string.google_maps_key), // your API key
                    source = source, // Source from where you want to draw path
                    destination = destination, // destination to where you want to draw path
                    context = requireContext() // Activity context
                ) {

                    // drawRouteOnMap is a extension function which takes the required parameters and draws the polyline on the map
                    // it?.duration
                }
            }
        }
    }

    private fun getDestination(location: String): LatLng {
        var addressList: List<Address>? = null

        val geocoder = Geocoder(requireContext())
        try {
            addressList = geocoder.getFromLocationName(location, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val address: Address = addressList!![0]
        return LatLng(address.latitude, address.longitude)
    }

    private fun setTitle(title: String) {
        (activity as MainActivity).supportActionBar?.title = title
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDestroy() {
        super.onDestroy()
        updateLocationTracking(false)
    }
}
