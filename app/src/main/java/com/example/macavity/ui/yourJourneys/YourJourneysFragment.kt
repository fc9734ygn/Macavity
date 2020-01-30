package com.example.macavity.ui.yourJourneys

import androidx.lifecycle.ViewModelProviders
import com.example.macavity.ui.base.HomeFragment
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_your_journeys")
open class YourJourneysFragment : HomeFragment() {

    private lateinit var vm: YourJourneysViewModel

    @AfterViews
    fun afterViews(){
        vm = ViewModelProviders.of(this).get(YourJourneysViewModel::class.java)
    }
}