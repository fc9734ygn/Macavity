package com.example.macavity.ui.addJourney

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.example.macavity.R

import com.example.macavity.ui.base.HomeFragment
import kotlinx.android.synthetic.main.fragment_add_journey.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_add_journey")
open class AddJourneyFragment : HomeFragment() {

    private lateinit var vm: AddJourneyViewModel

    @AfterViews
    fun afterViews(){
        vm = ViewModelProviders.of(this).get(AddJourneyViewModel::class.java)
        initToolbar()
    }

    private fun initToolbar(){
        toolbar.setStartIcon(R.drawable.ic_arrow_back).setEndIcon(R.drawable.ic_save)
        toolbar.startIconListener = {activity!!.onBackPressed()}
        //todo: add journey
        toolbar.endIconListener = {}
    }
}
