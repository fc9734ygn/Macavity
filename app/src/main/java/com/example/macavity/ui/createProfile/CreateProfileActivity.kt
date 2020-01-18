package com.example.macavity.ui.createProfile

import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_create_profile.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.CheckedChange
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.TextChange

@EActivity(resName = "activity_create_profile")
open class CreateProfileActivity : BaseActivity() {

    private lateinit var vm: CreateProfileViewModel

    @AfterViews
    fun afterViews(){
        vm = ViewModelProviders.of(this).get(CreateProfileViewModel::class.java)
        initToolbar()
        setAvatarImage("https://i.ytimg.com/vi/OIZqAOBJNOw/hqdefault.jpg")
    }

    private fun initToolbar(){
        toolbar.setTitle(getString(R.string.edit_profile_toolbar_title))
            .setStartIcon(R.drawable.ic_arrow_back)
            .setEndIcon(R.drawable.ic_save)

        toolbar.startIconListener = { onBackPressed() }
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
}