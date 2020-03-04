package com.example.macavity.ui.editJourney

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.macavity.ui.base.HomeFragment
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_edit_journey")
open class EditJourneyFragment : HomeFragment() {

    private lateinit var vm: EditJourneyViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProviders.of(this, viewModelFactory).get(EditJourneyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
