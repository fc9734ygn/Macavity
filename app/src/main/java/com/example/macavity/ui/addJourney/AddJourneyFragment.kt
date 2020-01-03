package com.example.macavity.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.macavity.R
import com.example.macavity.ui.base.BaseFragment
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_add_journey")
open class AddJourneyFragment : BaseFragment() {

    private lateinit var vm: AddJourneyViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProviders.of(this).get(AddJourneyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
