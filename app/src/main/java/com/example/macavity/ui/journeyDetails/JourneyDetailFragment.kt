package com.example.macavity.ui.journeyDetails

import androidx.lifecycle.ViewModelProviders
import com.example.macavity.R
import com.example.macavity.ui.base.HomeFragment
import kotlinx.android.synthetic.main.fragment_journey_detail.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_journey_detail")
open class JourneyDetailFragment : HomeFragment(){

    private lateinit var vm: JourneyDetailsViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(JourneyDetailsViewModel::class.java)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_arrow_back)
            .setTitle(getString(R.string.toolbar_title_journey_details))

        toolbar.startIconListener = { requireActivity().onBackPressed() }
    }
}