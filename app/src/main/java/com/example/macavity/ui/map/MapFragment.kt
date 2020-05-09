package com.example.macavity.ui.map

import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.macavity.R
import com.example.macavity.data.models.local.User
import com.example.macavity.ui.home.HomeFragment
import com.example.macavity.utils.MARKER_BOUNDS_PADDING_IN_PIXELS
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment


@EFragment(resName = "fragment_map")
open class MapFragment : HomeFragment() {

    private lateinit var vm: MapViewModel
    private lateinit var map: GoogleMap
    private val membersObserver = Observer<List<User>> { addMarkers(it) }

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(MapViewModel::class.java)
        vm.members.observe(this, membersObserver)
        initToolbar()
        initMap()
    }

    private fun addMarkers(groupMembers: List<User>) {
        if (groupMembers.isNullOrEmpty()) return
        map.clear()

        val markers = mutableListOf<MarkerOptions>()

        groupMembers.forEach { user ->

            val destinationMarker =
                MarkerOptions()
                    .position(LatLng(user.destination.latitude, user.destination.longitude))
                    .title(user.name + getString(R.string.map_window_title_destination))
                    .snippet(user.destination.address)
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE
                        )
                    )

            val startingPositionMarker =
                MarkerOptions()
                    .position(LatLng(user.home.latitude, user.home.longitude))
                    .title(user.name + getString(R.string.map_window_title_home))
                    .snippet(user.home.address)
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE
                        )
                    )

            markers.add(destinationMarker)
            markers.add(startingPositionMarker)

            val m1 = map.addMarker(destinationMarker)
            val m2 = map.addMarker(startingPositionMarker)

            //passing avatar url through tag as title and snippet are already being used
            m1.tag = user
            m2.tag = user
        }

        val builder = LatLngBounds.Builder()
        for (marker in markers) {
            builder.include(marker.position)
        }

        val bounds = builder.build()
        val padding = MARKER_BOUNDS_PADDING_IN_PIXELS
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)

        map.moveCamera(cameraUpdate)
        map.animateCamera(cameraUpdate)

    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->
            map = googleMap
            map.setInfoWindowAdapter(CustomInfoWindowAdapter(layoutInflater))
            map.setOnInfoWindowClickListener {
                val user = it.tag as User
                val action =
                    MapFragment_Directions.actionMapFragmentToProfileFragment(user.id)
                findNavController().navigate(action)
            }
        }

    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_map))

        toolbar.startIconListener = { openDrawer() }
    }
}
