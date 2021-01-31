package com.fred.earthquaketracker.features.home.ui

import android.content.Context
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.fred.earthquaketracker.R
import com.fred.earthquaketracker.features.home.controller.MainActivity
import com.fred.earthquaketracker.features.home.viewmodels.EarthquakeListViewModel
import com.fred.earthquaketracker.features.home.viewmodels.EarthquakeSpotViewModel
import com.fred.earthquaketracker.features.home.views.EarthquakeSpotViewHolder

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

/**
 * Include google map fragment, can be optimized with custom styles
 */
class EarthquakeSpotMapFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val spotViewModel: EarthquakeSpotViewModel by activityViewModels { viewModelFactory }

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
        spotViewModel.currentPickedEarthquakeLocation.observe(requireActivity(), {
            LatLng(it.first.toDouble(), it.second.toDouble()).run {
                googleMap.addMarker(
                    MarkerOptions().position(this)
                        .title("Earthquake at Lat: $latitude, Lng: $longitude")
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(this))
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earthquake_spot_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).homeComponent.inject(this)
    }

    companion object {
        val TAG = EarthquakeSpotMapFragment::class.simpleName
    }
}