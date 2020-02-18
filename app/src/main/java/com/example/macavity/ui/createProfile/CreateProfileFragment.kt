package com.example.macavity.ui.createProfile

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.ui.base.AuthFragment
import com.example.macavity.utils.RC_AUTO_COMPLETE_PLACE_DESTINATION
import com.example.macavity.utils.RC_AUTO_COMPLETE_PLACE_LOCATION
import com.example.macavity.utils.isValidPhoneNumber
import com.example.macavity.utils.isValidEmail
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.fragment_create_profile.*
import org.androidannotations.annotations.*

@EFragment(resName = "fragment_create_profile")
open class CreateProfileFragment : AuthFragment() {

    private lateinit var vm: CreateProfileViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(CreateProfileViewModel::class.java)
        initToolbar()
        setAvatarImage("https://media.gettyimages.com/photos/businessman-wearing-eyeglasses-picture-id825083358?b=1&k=6&m=825083358&s=612x612&w=0&h=SV2xnROuodWTh-sXycr-TULWi-bdlwBDXJkcfCz2lLc=")
    }

    private fun initToolbar() {
        toolbar.setTitle(getString(R.string.profile_create_toolbar_title))
            .setStartIcon(R.drawable.ic_arrow_back)
            .setEndIcon(R.drawable.ic_save)

        toolbar.startIconListener = { requireActivity().onBackPressed() }
        //todo: send new user data
        toolbar.endIconListener = { verifyUserInput() }
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
        if (isDataCorrect){
            //todo: create profile
            findNavController().navigate(R.id.action_createProfileFragment__to_createGroupFragment_)
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

    private fun autoCompleteRequest(requestCode: Int) {
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN, listOf(Place.Field.ADDRESS)
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
        when (requestCode) {
            RC_AUTO_COMPLETE_PLACE_LOCATION -> location.text =
                Autocomplete.getPlaceFromIntent(intent).address
            RC_AUTO_COMPLETE_PLACE_DESTINATION -> destination.text =
                Autocomplete.getPlaceFromIntent(intent).address
        }
    }

    @TextChange(resName = ["name"])
    fun onNameTextChange() {
        name_title.visibility = if (name.text.isNotBlank()) View.VISIBLE else View.INVISIBLE
        name.background = resources.getDrawable(R.drawable.background_grey_rounded, null)
    }

    @TextChange(resName = ["phone"])
    fun onPhoneTextChange() {
        phone_title.visibility = if (phone.text.isNotBlank()) View.VISIBLE else View.INVISIBLE
        phone.background = resources.getDrawable(R.drawable.background_grey_rounded, null)
    }

    @TextChange(resName = ["email"])
    fun onEmailTextChange() {
        email_title.visibility = if (email.text.isNotBlank()) View.VISIBLE else View.INVISIBLE
        email.background = resources.getDrawable(R.drawable.background_grey_rounded, null)
    }

    @TextChange(resName = ["car_number_plate"])
    fun onCarNumberTextChange() {
        car_number_plate_title.visibility =
            if (car_number_plate.text.isNotBlank()) View.VISIBLE else View.INVISIBLE
        car_number_plate.background =
            resources.getDrawable(R.drawable.background_grey_rounded, null)
    }

    @TextChange(resName = ["car_seats"])
    fun onCarSeatsTextChange() {
        car_seats_title.visibility =
            if (car_seats.text.isNotBlank()) View.VISIBLE else View.INVISIBLE
        car_seats.background = resources.getDrawable(R.drawable.background_grey_rounded, null)
    }

    @CheckedChange(resName = ["driver_switch"])
    fun onSwitchChanged() {
        val isChecked = driver_switch.isChecked
        car_number_plate.visibility = if (isChecked) View.VISIBLE else View.GONE
        car_number_plate_title.visibility =
            if (isChecked && car_number_plate.text.isNotBlank()) View.VISIBLE else View.INVISIBLE
        car_seats.visibility = if (isChecked) View.VISIBLE else View.GONE
        car_seats_title.visibility =
            if (isChecked && car_seats.text.isNotBlank()) View.VISIBLE else View.INVISIBLE
    }

    @Click(resName = ["location"])
    fun onLocationClick() {
        autoCompleteRequest(RC_AUTO_COMPLETE_PLACE_LOCATION)
    }

    @Click(resName = ["destination"])
    fun onDestinationClick() {
        autoCompleteRequest(RC_AUTO_COMPLETE_PLACE_DESTINATION)
    }
}