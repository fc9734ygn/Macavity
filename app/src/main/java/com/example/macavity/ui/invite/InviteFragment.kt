package com.example.macavity.ui.invite

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.macavity.R
import com.example.macavity.ui.home.HomeFragment
import com.example.macavity.utils.copyToClipboard
import com.example.macavity.utils.sendEmail
import kotlinx.android.synthetic.main.fragment_invite.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment


@EFragment(resName = "fragment_invite")
open class InviteFragment : HomeFragment() {

    private lateinit var vm: InviteViewModel

    private val invitationCodeObserver = Observer<String> {
        link.text = it
    }

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(InviteViewModel::class.java)
        vm.invitationCode.observe(this, invitationCodeObserver)
        vm.getInvitationCode()
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_arrow_back)
            .setTitle(getString(R.string.invite_toolbar_title))
        toolbar.startIconListener = { requireActivity().onBackPressed() }
    }

    @Click(resName = ["email_button"])
    fun sendEmailToAddress() {
        sendEmail(
            context!!,
            "",
            getString(R.string.invite_email_subject),
            String.format(getString(R.string.invite_email_body), link.text)
        )
    }

    @Click(resName = ["copy_button"])
    fun copyToClipboard() {
        copyToClipboard(requireContext(), link.text.toString())
        toast(getString(R.string.invite_copy_success))
    }
}