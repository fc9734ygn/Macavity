package com.example.macavity.ui.editJourney

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

import com.example.macavity.ui.base.BaseFragment
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_edit_journey")
open class EditJourneyFragment : BaseFragment() {

    private lateinit var vm: EditJourneyViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProviders.of(this).get(EditJourneyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
