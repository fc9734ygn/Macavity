package com.example.macavity.ui.journeyDetails

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.Journey
import com.example.macavity.data.models.User
import com.example.macavity.ui.base.HomeFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_journey_detail.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_journey_detail")
open class JourneyDetailFragment : HomeFragment() {

    private lateinit var vm: JourneyDetailsViewModel
    private lateinit var map: GoogleMap

    //TODO: pass user id
    private val passengersAdapter =
        PassengersAdapter { findNavController().navigate(R.id.action_journeyDetailFragment__to_profileFragment_) }

    //TODO: use real data
    private val dummyJourney = Journey(
        "123",
        User(
            "123",
            "John",
            "Main Street 25",
            "Knight Ave 87",
            Pair(12.2, 51.2),
            Pair(21.2, 13.2),
            "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "john.smith@gmail.com",
            "+44123123123",
            true,
            "AH3K24",
            4,
            41,
            23
        ), 4, 2, listOf(
            User(
                "123",
                "Rachel",
                "Main Street 25",
                "Knight Ave 87",
                Pair(12.2, 51.2),
                Pair(21.2, 13.2),
                "https://media.istockphoto.com/photos/young-longhaired-smiling-woman-in-white-shirt-picture-id965523228?k=6&m=965523228&s=612x612&w=0&h=qeVmQfjRq1QWxaLdxLdF_IaXahI-dqt9UYcunaHUqA4=",
                "john.smith@gmail.com",
                "+44123123123",
                true,
                "AH3K24",
                4,
                2,
                23
            ),
            User(
                "123",
                "Erick",
                "Main Street 25",
                "Knight Ave 87",
                Pair(12.2, 51.2),
                Pair(21.2, 13.2),
                "https://images.pexels.com/photos/736716/pexels-photo-736716.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "john.smith@gmail.com",
                "+44123123123",
                true,
                "AH3K24",
                4,
                88,
                23
            )
        ), 123123123
    )

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(JourneyDetailsViewModel::class.java)
        initToolbar()
        initMap()
        setDriverData(dummyJourney.driver)
        setJourneyData(dummyJourney)
        initPassengersRecyclerView()
    }

    private fun initPassengersRecyclerView() {
        passengers_rv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        passengers_rv.adapter = passengersAdapter
        passengers_rv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )
        //TODO: user real data
        passengersAdapter.submitList(dummyJourney.passengers)
    }

    private fun setJourneyData(journey: Journey) {
        //TODO: format date
        leaving_at.text = String.format(getString(R.string.leaving_at), journey.timeStamp)
        seats.text = String.format(getString(R.string.seats), journey.takenSeats, journey.freeSeats)
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_arrow_back)
            .setTitle(getString(R.string.toolbar_title_journey_details))

        toolbar.startIconListener = { requireActivity().onBackPressed() }
    }

    private fun initMap() {
        //TODO: set correct options/markers/zoom etc.
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->
            map = googleMap
            val london = LatLng(52.0, 0.0)
            map.uiSettings.isScrollGesturesEnabled = false
            map.uiSettings.isRotateGesturesEnabled = false
            map.addMarker(MarkerOptions().position(london).title("Marker"))
            map.moveCamera(CameraUpdateFactory.newLatLng(london))
        }
    }

    private fun setDriverData(driver: User) {
        name_driver.text = driver.name
        stat_driver.text = String.format(getString(R.string.driver_stat), driver.driverStat)
        setDriverAvatar(driver.avatarUrl)
    }

    private fun setDriverAvatar(url: String) {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(avatar_driver)
    }

    @Click(resName = ["driver_card"])
    fun goToDriverProfile() {
        //TODO: pass driver id
        findNavController().navigate(R.id.action_journeyDetailFragment__to_profileFragment_)
    }
}