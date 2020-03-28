package com.example.macavity.ui.editProfile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.User
import com.example.macavity.ui.base.HomeFragment
import com.example.macavity.ui.home.HomeActivity
import com.example.macavity.utils.RC_AUTO_COMPLETE_PLACE_DESTINATION
import com.example.macavity.utils.RC_AUTO_COMPLETE_PLACE_LOCATION
import com.example.macavity.utils.isValidEmail
import com.example.macavity.utils.isValidPhoneNumber
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.CheckedChange
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment


@EFragment(resName = "fragment_edit_profile")
open class EditProfileFragment : HomeFragment() {

    private lateinit var vm: EditProfileViewModel
    private lateinit var currentUser: User

    private val userObserver = Observer<User?> {
        if (it != null) {
            currentUser = it
            setInitialUserData()

            //enabling save button
            toolbar.setEndIcon(R.drawable.ic_save)
            toolbar.endIconListener = { verifyUserInput() }
        }
    }
    private val profileEditSuccessObserver = Observer<Boolean> {
        if (it) {
            hideKeyboard(requireContext(), this.view!!)
            requireActivity().onBackPressed()
        }
    }

    @AfterViews
    fun afterViews() {
        initViewModel()
        initToolbar()
    }

    private fun initViewModel() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(EditProfileViewModel::class.java)
        vm.currentUser.observe(this, userObserver)
        vm.profileUpdatedSuccess.observe(this, profileEditSuccessObserver)
        vm.fetchUser()
    }

    private fun setInitialUserData() {
        name.setText(currentUser.name)
        phone.setText(currentUser.phoneNumber)
        email.setText(currentUser.email)
        location.text = currentUser.home.address
        destination.text = currentUser.destination.address
        setAvatarImage(currentUser.avatarUrl)
        driver_switch.isChecked = currentUser.driver
        vm.home = currentUser.home
        vm.destination = currentUser.destination
        if (currentUser.driver) {
            car_number_plate.setText(currentUser.carNumberPlate)
            car_seats.setText(currentUser.carFreeSeats.toString())
        }
    }

    private fun initToolbar() {
        toolbar.setTitle(getString(R.string.edit_profile_toolbar_title))
            .setStartIcon(R.drawable.ic_arrow_back)

        toolbar.startIconListener = { requireActivity().onBackPressed() }
    }

    private fun verifyUserInput() {
        var isDataCorrect = true
        if (name.text.isNullOrBlank()) {
            showRedBorder(name)
            isDataCorrect = false
        }
        if (phone.text.isNullOrBlank() || !isValidPhoneNumber(phone.text)) {
            showRedBorder(phone)
            isDataCorrect = false
        }
        if (email.text.isNullOrBlank() || !isValidEmail(email.text)) {
            showRedBorder(email)
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
        if (driver_switch.isChecked && car_number_plate.text.isNullOrBlank()) {
            showRedBorder(car_number_plate)
            isDataCorrect = false
        }
        if (driver_switch.isChecked && car_seats.text.isNullOrBlank()) {
            showRedBorder(car_seats)
            isDataCorrect = false
        }
        if (isDataCorrect) {
            vm.saveProfileChanges(
                name.text.toString(),
                currentUser.avatarUrl,
                email.text.toString(),
                phone.text.toString(),
                driver_switch.isChecked,
                car_number_plate.text.toString(),
                if (car_seats.text.toString().isNotBlank())
                    car_seats.text.toString().toInt() else null
            )
        }
    }

    private fun showRedBorder(view: View) {
        view.background = resources.getDrawable(R.drawable.background_grey_rounded_red_border, null)
    }

    private fun setAvatarImage(url: String) {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(profile_avatar)
    }

    @CheckedChange(resName = ["driver_switch"])
    fun onSwitchChanged() {
        val visibility = if (driver_switch.isChecked) View.VISIBLE else View.GONE
        car_number_plate.visibility = visibility
        car_number_plate_title.visibility = visibility
        car_seats.visibility = visibility
        car_seats_title.visibility = visibility
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            onAddressReceived(requestCode, data!!)
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            val status: Status = Autocomplete.getStatusFromIntent(data!!)
            toast(status.statusMessage)
        }
    }

//    private fun onAddressReceived(requestCode: Int, intent: Intent) {
//        when (requestCode) {
//            RC_AUTO_COMPLETE_PLACE_LOCATION -> location.text =
//                Autocomplete.getPlaceFromIntent(intent).address
//            RC_AUTO_COMPLETE_PLACE_DESTINATION -> destination.text =
//                Autocomplete.getPlaceFromIntent(intent).address
//        }
//    }

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
                vm.home = Location(
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

    @Click(resName = ["location"])
    fun onLocationClick() {
        autoCompleteRequest(RC_AUTO_COMPLETE_PLACE_LOCATION)
    }

    @Click(resName = ["destination"])
    fun onDestinationClick() {
        autoCompleteRequest(RC_AUTO_COMPLETE_PLACE_DESTINATION)
    }

    private fun autoCompleteRequest(requestCode: Int) {
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN, listOf(Place.Field.ADDRESS)
        )
            .build(context!!)
        startActivityForResult(intent, requestCode)
    }
}
