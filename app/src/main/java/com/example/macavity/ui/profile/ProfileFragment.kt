package com.example.macavity.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.ui.base.BaseFragment
import com.example.macavity.utils.callPhoneNumber
import com.example.macavity.utils.sendEmail
import kotlinx.android.synthetic.main.fragment_map.toolbar
import kotlinx.android.synthetic.main.fragment_profile.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment


@EFragment(resName = "fragment_profile")
open class ProfileFragment : BaseFragment() {

    private lateinit var vm: ProfileViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        initToolbar()
        setAvatarImage("https://i.ytimg.com/vi/OIZqAOBJNOw/hqdefault.jpg")

        //TODO: use real data
        name.text = "John"
        phone.text = "+44983489798342"
        email.text = "John.doe@gmail.com"
        driver_stat.text = "5"
        passenger_stat.text = "23"
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setEndIcon(R.drawable.ic_edit)
            .setTitle(getString(R.string.toolbar_title_profile))

        toolbar.startIconListener = { openDrawer() }
        //TODO: disable if not your profile
        toolbar.endIconListener = { goToEditProfile() }
    }

    private fun setAvatarImage(url: String) {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(profile_avatar)
    }

    private fun goToEditProfile() {
        findNavController().navigate(R.id.action_profileFragment__to_editProfileFragment_)
    }

    @Click(resName = ["phone_button"])
    fun callPhone() {
        callPhoneNumber(activity!!, phone.text.toString())
    }

    @Click(resName = ["email_button"])
    fun sendEmailToAddress() {
        sendEmail(context!!, email.text.toString(), null, null)
    }


}
