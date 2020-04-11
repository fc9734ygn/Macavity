package com.example.macavity.ui.addJourney

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.macavity.R
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.User

import com.example.macavity.ui.home.HomeFragment
import com.example.macavity.ui.widgets.TimePickerFragment
import com.example.macavity.utils.RC_AUTO_COMPLETE_PLACE_DESTINATION
import com.example.macavity.utils.RC_AUTO_COMPLETE_PLACE_LOCATION
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.fragment_add_journey.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

@EFragment(resName = "fragment_add_journey")
open class AddJourneyFragment : HomeFragment() {

    private lateinit var vm: AddJourneyViewModel
    val args: AddJourneyFragment_Args by navArgs()

    private val journeyCreatedSuccessObserver = Observer<Boolean> {
        if (it) {
            requireActivity().onBackPressed()
        }
    }

    private val userObserver = Observer<User> {
        setInitialData(it)
    }

    @AfterViews
    fun afterViews() {
        initViewModel()
        initToolbar()
    }

    private fun initViewModel() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(AddJourneyViewModel::class.java)
        vm.journeyCreatedSuccess.observe(this, journeyCreatedSuccessObserver)
        vm.currentUser.observe(this, userObserver)
        vm.fetchUser()
    }

    private fun setInitialData(user: User) {
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM")
        date.text = dateFormatter.format(args.dateWithOutTime)
        vm.startingLocation = user.home
        vm.destination = user.destination
        location.text = user.home.address
        destination.text = user.destination.address
        free_seats.setText(user.carFreeSeats.toString())
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_arrow_back)
            .setEndIcon(R.drawable.ic_add)
            .setTitle(getString(R.string.add_journey_toolbar_title))

        toolbar.startIconListener = { requireActivity().onBackPressed() }
        toolbar.endIconListener = { verifyUserInput() }
    }

    private fun verifyUserInput() {
        var isDataCorrect = true
        if (time.text.isNullOrBlank()) {
            showRedBorder(time)
            isDataCorrect = false
        }
        if (free_seats.text.isNullOrBlank()) {
            showRedBorder(free_seats)
            isDataCorrect = false
        }
        if (location.text.isNullOrBlank()) {
            showRedBorder(location)
            isDataCorrect = false
        }
        if (destination.text.isNullOrBlank()) {
            showRedBorder(destination)
            isDataCorrect = false
        }
        if (isDataCorrect) {
            vm.createJourney(free_seats.text.toString().toInt(), note.text.toString())
        }
    }

    private fun showRedBorder(view: View) {
        view.background = resources.getDrawable(R.drawable.background_grey_rounded_red_border, null)
    }

    private fun autoCompleteRequest(requestCode: Int) {
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN,
            listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.ID)
        )
            .build(context!!)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            onAddressReceived(requestCode, data!!)
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            val status: Status = Autocomplete.getStatusFromIntent(data!!)
            toast(status.statusMessage)
        }
    }

    private fun onAddressReceived(requestCode: Int, intent: Intent) {
        val address = Autocomplete.getPlaceFromIntent(intent).address
        val placeId = Autocomplete.getPlaceFromIntent(intent).id
        val latLng = Autocomplete.getPlaceFromIntent(intent).latLng

        if (address.isNullOrBlank() || placeId == null || latLng == null) {
            toast(getString(R.string.error_incorrect_address))
            return
        }

        when (requestCode) {
            RC_AUTO_COMPLETE_PLACE_LOCATION -> {
                location.text = address
                location.background =
                    resources.getDrawable(R.drawable.background_grey_rounded, null)
                vm.startingLocation =
                    Location(
                        placeId,
                        address,
                        latLng.latitude,
                        latLng.longitude
                    )
            }
            RC_AUTO_COMPLETE_PLACE_DESTINATION -> {
                destination.text = address
                destination.background =
                    resources.getDrawable(R.drawable.background_grey_rounded, null)
                vm.destination = Location(
                    placeId,
                    address,
                    latLng.latitude,
                    latLng.longitude
                )
            }
        }
    }

    private fun setTime(hours: Int, minutes: Int) {
        val hoursFormatted = if (hours.toString().length <= 1) "0$hours" else hours.toString()
        val minutesFormatted =
            if (minutes.toString().length <= 1) "0$minutes" else minutes.toString()
        time.text =
            String.format(getString(R.string.add_journey_time), hoursFormatted, minutesFormatted)
    }

    @Click(resName = ["time"])
    fun showTimePicker() {
        TimePickerFragment { hours, minutes ->
            setTime(hours, minutes)
            val time = LocalTime.of(hours, minutes)
            vm.dateTime = LocalDateTime.of(args.dateWithOutTime, time)
        }.show(requireFragmentManager(), "timePicker")
        time.background = resources.getDrawable(R.drawable.background_grey_rounded, null)
    }

    @Click(resName = ["free_seats"])
    fun onFreeSeatsClick() {
        free_seats.background = resources.getDrawable(R.drawable.background_grey_rounded, null)
    }

    @Click(resName = ["location"])
    fun onLocationClick() {
        autoCompleteRequest(RC_AUTO_COMPLETE_PLACE_LOCATION)
        location.background = resources.getDrawable(R.drawable.background_grey_rounded, null)
    }

    @Click(resName = ["destination"])
    fun onDestinationClick() {
        autoCompleteRequest(RC_AUTO_COMPLETE_PLACE_DESTINATION)
        destination.background = resources.getDrawable(R.drawable.background_grey_rounded, null)
    }
}
