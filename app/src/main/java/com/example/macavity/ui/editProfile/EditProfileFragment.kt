package com.example.macavity.ui.editProfile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.User
import com.example.macavity.ui.base.HomeFragment
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
    private lateinit var user: User

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(EditProfileViewModel::class.java)
        initToolbar()

        //TODO: use real data
        val loc1 = Location(
            "A",
            "Kings Ave 22",
            2.0,
            2.1
        )
        val avatar = "https://media.gettyimages.com/photos/businessman-wearing-eyeglasses-picture-id825083358?b=1&k=6&m=825083358&s=612x612&w=0&h=SV2xnROuodWTh-sXycr-TULWi-bdlwBDXJkcfCz2lLc="
        user = User(
            "123",
            "Alan",
            loc1,
            loc1,
            avatar,
            "johndoe@gmail.com",
            "+442131233212",
            true,
            "GH7G HGC",
            3,
            5,
            23, "A"

        )
        setInitialUserData()
    }

    private fun setInitialUserData() {
        name.setText(user.name)
        phone.setText(user.phoneNumber)
        email.setText(user.email)
        location.text = user.home.address
        destination.text = user.destination.address
        setAvatarImage(user.avatarUrl)
        driver_switch.isChecked = user.isDriver
        car_number_plate.setText(user.carNumberPlate)
        car_seats.setText(user.carFreeSeats.toString())
    }

    private fun initToolbar() {
        toolbar.setTitle(getString(R.string.edit_profile_toolbar_title))
            .setStartIcon(R.drawable.ic_arrow_back)
            .setEndIcon(R.drawable.ic_save)

        toolbar.startIconListener = { requireActivity().onBackPressed() }
        //TODO: save changes
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
            //todo: save changes instead of onbackpressed
            requireActivity().onBackPressed()
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

    private fun onAddressReceived(requestCode: Int, intent: Intent) {
        when (requestCode) {
            RC_AUTO_COMPLETE_PLACE_LOCATION -> location.text = Autocomplete.getPlaceFromIntent(intent).address
            RC_AUTO_COMPLETE_PLACE_DESTINATION -> destination.text = Autocomplete.getPlaceFromIntent(intent).address
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
