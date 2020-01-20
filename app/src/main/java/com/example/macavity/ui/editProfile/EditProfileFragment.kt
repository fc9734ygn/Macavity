package com.example.macavity.ui.editProfile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.User
import com.example.macavity.ui.base.BaseFragment
import com.example.macavity.utils.RC_AUTO_COMPLETE_PLACE_DESTINATION
import com.example.macavity.utils.RC_AUTO_COMPLETE_PLACE_LOCATION
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
open class EditProfileFragment : BaseFragment() {

    private lateinit var vm: EditProfileViewModel
    private lateinit var user: User

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        initToolbar()
        toggleBottomNav(false)
        //TODO: use real data
        val avatar = "https://i.ytimg.com/vi/OIZqAOBJNOw/hqdefault.jpg"
        user = User(
            "123",
            "Alan",
            "45 Knight Avenue",
            "55 Wolf street",
            Pair(1.2, 0.2),
            Pair(2.0, 3.1),
            avatar,
            "johndoe@gmail.com",
            "+442131233212",
            true,
            "GH7G HGC",
            3,
            5,
            23
        )
        setInitialUserData()
    }

    private fun setInitialUserData() {
        name.setText(user.name)
        phone.setText(user.phoneNumber)
        email.setText(user.email)
        location.text = user.locationAddress
        destination.text = user.destinationAddress
        setAvatarImage(user.avatarUrl)
        driver_switch.isChecked = user.isDriver
        car_number_plate.setText(user.carNumberPlate)
        car_seats.setText(user.carFreeSeats.toString())
    }

    private fun initToolbar() {
        toolbar.setTitle(getString(R.string.edit_profile_toolbar_title))
            .setStartIcon(R.drawable.ic_arrow_back)
            .setEndIcon(R.drawable.ic_save)

        toolbar.startIconListener = { activity!!.onBackPressed() }
        //TODO: save changes
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
            showError(status.statusMessage)
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
