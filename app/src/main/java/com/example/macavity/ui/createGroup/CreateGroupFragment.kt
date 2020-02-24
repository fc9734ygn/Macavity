package com.example.macavity.ui.createGroup

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.macavity.R
import com.example.macavity.ui.base.AuthFragment
import com.example.macavity.ui.home.HomeActivity_
import kotlinx.android.synthetic.main.fragment_create_group.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_create_group")
open class CreateGroupFragment : AuthFragment() {

    lateinit var vm: CreateGroupViewModel

    private val groupCreationObserver = Observer<Boolean> {
        if (it) {
            HomeActivity_.intent(requireContext()).start()
        }
    }

    @AfterViews
    fun afterViews(){
        vm = ViewModelProviders.of(this, viewModelFactory).get(CreateGroupViewModel::class.java)
        vm.groupCreatedSuccess.observe(this,groupCreationObserver)
        initToolbar()
    }

    private fun initToolbar(){
        toolbar.setTitle(getString(R.string.create_group_toolbar_title))
    }

    @Click(resName = ["create_group_button"])
    fun createGroup(){
        vm.getCurrentUserId()
    }
}