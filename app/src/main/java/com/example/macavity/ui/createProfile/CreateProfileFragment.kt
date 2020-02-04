package com.example.macavity.ui.createProfile

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.ui.base.AuthFragment
import com.example.macavity.utils.RC_AUTO_COMPLETE_PLACE_DESTINATION
import com.example.macavity.utils.RC_AUTO_COMPLETE_PLACE_LOCATION
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
    fun afterViews(){
        vm = ViewModelProviders.of(this).get(CreateProfileViewModel::class.java)
        initToolbar()
        setAvatarImage("https://i.ytimg.com/vi/OIZqAOBJNOw/hqdefault.jpg")
    }

    private fun initToolbar(){
        toolbar.setTitle(getString(R.string.profile_create_toolbar_title))
            .setStartIcon(R.drawable.ic_arrow_back)
            .setEndIcon(R.drawable.ic_save)

        toolbar.startIconListener = { requireActivity().onBackPressed() }
        //todo: finish nav
        toolbar.endIconListener = {}
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
            RC_AUTO_COMPLETE_PLACE_LOCATION -> location.text = Autocomplete.getPlaceFromIntent(intent).address
            RC_AUTO_COMPLETE_PLACE_DESTINATION -> destination.text = Autocomplete.getPlaceFromIntent(intent).address
        }
    }

    @TextChange(resName = ["name"])
    fun onNameTextChange() {
        name_title.visibility = if (name.text.isNotBlank()) View.VISIBLE else View.INVISIBLE
    }

    @TextChange(resName = ["phone"])
    fun onPhoneTextChange() {
        phone_title.visibility = if (phone.text.isNotBlank()) View.VISIBLE else View.INVISIBLE
    }

    @TextChange(resName = ["email"])
    fun onEmailTextChange() {
        email_title.visibility = if (email.text.isNotBlank()) View.VISIBLE else View.INVISIBLE
    }

    @TextChange(resName = ["car_number_plate"])
    fun onCarNumberTextChange() {
        car_number_plate_title.visibility =
            if (car_number_plate.text.isNotBlank()) View.VISIBLE else View.INVISIBLE
    }

    @TextChange(resName = ["car_seats"])
    fun onCarSeatsTextChange() {
        car_seats_title.visibility = if (car_seats.text.isNotBlank()) View.VISIBLE else View.INVISIBLE
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