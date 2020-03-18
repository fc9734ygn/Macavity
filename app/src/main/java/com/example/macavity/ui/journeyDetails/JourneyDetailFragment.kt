package com.example.macavity.ui.journeyDetails

import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.local.Journey
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.User
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
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter


@EFragment(resName = "fragment_journey_detail")
open class JourneyDetailFragment : HomeFragment() {

    private lateinit var vm: JourneyDetailsViewModel
    private lateinit var map: GoogleMap

    //TODO: pass user id
    private val passengersAdapter =
        PassengersAdapter { findNavController().navigate(R.id.action_journeyDetailFragment__to_profileFragment_) }

    //TODO: use real data
    val loc1 = Location(
        "A",
        "Kings Ave 22",
        2.0,
        2.1
    )
    private val dummyJourney = Journey(
        "123",
        User(
            "123",
            "John",
            loc1,
            loc1,
            "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "john.smith@gmail.com",
            "+44123123123",
            true,
            "AH3K24",
            4,
            41,
            23, "A"

        ), 4, listOf(
            User(
                "123",
                "Rachel",
                loc1,
                loc1,
                "https://media.istockphoto.com/photos/young-longhaired-smiling-woman-in-white-shirt-picture-id965523228?k=6&m=965523228&s=612x612&w=0&h=qeVmQfjRq1QWxaLdxLdF_IaXahI-dqt9UYcunaHUqA4=",
                "john.smith@gmail.com",
                "+44123123123",
                true,
                "AH3K24",
                4,
                2,
                23, "A"

            ),
            User(
                "123",
                "Erick",
                loc1,
                loc1,
                "https://images.pexels.com/photos/736716/pexels-photo-736716.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "john.smith@gmail.com",
                "+44123123123",
                true,
                "AH3K24",
                4,
                88,
                23, "A"

            )
        ), 123123123, "Will have to stop at a gas station", loc1, loc1
    )

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(JourneyDetailsViewModel::class.java)
        initToolbar()
        initMap()
        setData(dummyJourney)
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
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 10f))
        }
    }

    private fun setData(journey: Journey) {
        name_driver.text = journey.driver.name
        stat_driver.text = String.format(
            getString(R.string.journey_details_driver_stat),
            journey.driver.driverStat
        )

        setDate(journey.timestamp)

        seats.text = String.format(
            getString(R.string.journey_details_seats),
            journey.passengers.size,
            journey.freeSeats
        )
        car_number_plate.text = String.format(
            getString(R.string.journey_details_car_number_plate),
            journey.driver.carNumberPlate
        )
        drivers_note.text = journey.note
        drivers_note_card.visibility = if (journey.note.isNullOrBlank()) View.GONE else View.VISIBLE
        setDriverAvatar(journey.driver.avatarUrl)
    }

    private fun setDate(timeStamp: Long) {
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM HH:mm ")
        val date = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeStamp),
            ZoneId.systemDefault()
        )

        leaving_at.text = String.format(
            getString(R.string.journey_details_leaving_at),
            dateFormatter.format(date)
        )
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