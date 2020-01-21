package com.example.macavity.ui.map

import androidx.lifecycle.ViewModelProviders
import com.example.macavity.R
import com.example.macavity.ui.base.HomeFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment


@EFragment(resName = "fragment_map")
open class MapFragment : HomeFragment() {

    private lateinit var vm: MapViewModel
    private lateinit var map: GoogleMap

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(MapViewModel::class.java)
        initToolbar()
        toggleBottomNav(true)
        initMap()
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->
            map = googleMap
            val london = LatLng(52.0, 0.0)
            map.addMarker(MarkerOptions().position(london).title("Marker"))
            map.moveCamera(CameraUpdateFactory.newLatLng(london))
        }
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_map))

        toolbar.startIconListener = { openDrawer() }
    }

}
