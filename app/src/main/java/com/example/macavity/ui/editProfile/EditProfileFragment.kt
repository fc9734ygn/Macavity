package com.example.macavity.ui.editProfile

import androidx.lifecycle.ViewModelProviders
import android.view.View
import com.bumptech.glide.Glide
import com.example.macavity.R

import com.example.macavity.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.CheckedChange
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.TextChange

@EFragment(resName = "fragment_edit_profile")
open class EditProfileFragment : BaseFragment() {

    private lateinit var vm: EditProfileViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        initToolbar()
        toggleBottomNav(false)
        //TODO: use real data
        setAvatarImage("https://i.ytimg.com/vi/OIZqAOBJNOw/hqdefault.jpg")
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
}
