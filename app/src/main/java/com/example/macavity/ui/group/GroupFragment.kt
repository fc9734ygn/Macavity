package com.example.macavity.ui.group

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

import com.example.macavity.ui.base.BaseFragment
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_group")
open class GroupFragment : BaseFragment() {

    private lateinit var vm: GroupViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProviders.of(this).get(GroupViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
