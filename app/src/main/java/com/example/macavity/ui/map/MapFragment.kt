package com.example.macavity.ui.map

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.macavity.R
import com.example.macavity.data.models.local.User
import com.example.macavity.ui.base.HomeFragment
import com.example.macavity.utils.BOUNDS_PADDING_IN_PIXELS
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
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

        groupMembers.forEach {
            val destinationMarker = MarkerOptions().position(LatLng(it.home.latitude, it.home.longitude))
            val startingPositionMarker = MarkerOptions().position(LatLng(it.destination.latitude, it.destination.longitude))

            markers.add(destinationMarker)
            markers.add(startingPositionMarker)

            map.addMarker(destinationMarker)
            map.addMarker(startingPositionMarker)
        }

        val builder = LatLngBounds.Builder()
        for (marker in markers) {
            builder.include(marker.position)
        }

        val bounds = builder.build()
        val padding = BOUNDS_PADDING_IN_PIXELS
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)

        map.moveCamera(cameraUpdate)
        map.animateCamera(cameraUpdate)

    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->
            map = googleMap
        }
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_map))

        toolbar.startIconListener = { openDrawer() }
    }
}
