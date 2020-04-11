package com.example.macavity.ui.profile

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.local.User
import com.example.macavity.ui.home.HomeFragment
import com.example.macavity.utils.callPhoneNumber
import com.example.macavity.utils.sendEmail
import kotlinx.android.synthetic.main.fragment_map.toolbar
import kotlinx.android.synthetic.main.fragment_profile.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment


@EFragment(resName = "fragment_profile")
open class ProfileFragment : HomeFragment() {

    private lateinit var vm: ProfileViewModel
    val args: ProfileFragment_Args by navArgs()

    private val userObserver = Observer<User> {
        setUserData(it)
    }

    @AfterViews
    fun afterViews() {
        initViewModel()
        initToolbar()
    }

    private fun initViewModel() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)
        vm.user.observe(this, userObserver)

        //If no userId is passed when creating the fragment - fetching current user
        if (args.userId == null) {
            vm.fetchSelfUser()
        } else {
            vm.fetchUser(args.userId!!)
        }
    }

    private fun setUserData(user: User) {
        setAvatarImage(user.avatarUrl)
        name.text = user.name
        phone.text = user.phoneNumber
        email.text = user.email
        driver_stat.text = user.driverStat.toString()
        passenger_stat.text = user.passengerStat.toString()
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_arrow_back)
            .setTitle(getString(R.string.toolbar_title_profile))

        toolbar.startIconListener = { requireActivity().onBackPressed() }

        //If userId is passed when creating the fragment - its current user which means showing "edit profile" button
        if (args.userId == null) {
            toolbar.setEndIcon(R.drawable.ic_edit)
            toolbar.endIconListener = { goToEditProfile() }
        }
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
        callPhoneNumber(requireActivity(), phone.text.toString())
    }

    @Click(resName = ["email_button"])
    fun sendEmailToAddress() {
        sendEmail(context!!, email.text.toString(), null, null)
    }
}
