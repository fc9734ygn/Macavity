package com.example.macavity.ui.addJourney

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

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
