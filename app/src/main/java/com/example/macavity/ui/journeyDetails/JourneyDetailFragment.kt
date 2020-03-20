package com.example.macavity.ui.journeyDetails

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.local.JourneyDetails
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

    val args: JourneyDetailFragment_Args by navArgs()

    //TODO: pass user id
    private val passengersAdapter =
        PassengersAdapter { findNavController().navigate(R.id.action_journeyDetailFragment__to_profileFragment_) }

    private val passengersObserver = Observer<List<User>> {
        if (it.isNullOrEmpty()) {
            //todo: Show no passengers view
        } else {
            passengersAdapter.submitList(it)
        }
    }

    private val driverObserver = Observer<User> {
        setDriverData(it)
    }

    private val journeyObserver = Observer<JourneyDetails> {
        setJourneyData(it)
    }

    private val userIsDriverObserver = Observer<Boolean> {
        cancel_journey_button.visibility = if (it) View.VISIBLE else View.GONE
        cancel_booking_button.visibility = if (it) View.GONE else View.VISIBLE
        book_seat_button.visibility = if (it) View.GONE else View.VISIBLE
    }

    private val userIsPassengerObserver = Observer<Boolean> {
        cancel_booking_button.visibility = if (it) View.VISIBLE else View.GONE
        cancel_journey_button.visibility = if (it) View.VISIBLE else View.GONE
        book_seat_button.visibility = if (it) View.GONE else View.VISIBLE
    }

    @AfterViews
    fun afterViews() {
        initToolbar()
        initMap()
        initPassengersRecyclerView()
        initViewModel()
    }

    private fun initViewModel() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(JourneyDetailsViewModel::class.java)
        vm.journeyDetails.observe(this, journeyObserver)
        vm.driver.observe(this, driverObserver)
        vm.passengers.observe(this, passengersObserver)
        vm.currentUserIsDriver.observe(this, userIsDriverObserver)
        vm.currentUserIsPassenger.observe(this, userIsPassengerObserver)
        vm.fetchJourney(args.journeyId)
    }

    private fun initPassengersRecyclerView() {
        passengers_rv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        passengers_rv.adapter = passengersAdapter
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

    private fun setDriverData(driver: User) {
        name_driver.text = driver.name
        stat_driver.text = String.format(
            getString(R.string.journey_details_driver_stat),
            driver.driverStat
        )
        car_number_plate.text = String.format(
            getString(R.string.journey_details_car_number_plate),
            driver.carNumberPlate
        )

        setDriverAvatar(driver.avatarUrl)
    }

    private fun setJourneyData(journey: JourneyDetails) {
        setDate(journey.timestamp)

        val seatsTaken = if (journey.passengerIds.isNullOrEmpty()) 0 else journey.passengerIds.size
        seats.text = String.format(
            getString(R.string.journey_details_seats),
            seatsTaken,
            journey.freeSeats
        )

        drivers_note.text = journey.driversNote
        drivers_note_card.visibility =
            if (journey.driversNote.isNullOrBlank()) View.GONE else View.VISIBLE
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

    @Click(resName = ["book_seat_button"])
    fun bookSeat() {
        vm.bookSeat()
    }

    @Click(resName = ["cancel_journey_button"])
    fun cancelJourney() {
        vm.cancelJourney()
    }

    @Click(resName = ["cancel_booking_button"])
    fun cancelBooking() {
        vm.cancelBooking()
    }
}