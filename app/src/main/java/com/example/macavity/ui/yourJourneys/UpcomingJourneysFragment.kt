package com.example.macavity.ui.yourJourneys

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macavity.R
import com.example.macavity.data.models.local.Journey
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.UpcomingJourney
import com.example.macavity.data.models.local.User
import com.example.macavity.ui.base.HomeFragment
import com.example.macavity.ui.calendar.JourneysAdapter
import kotlinx.android.synthetic.main.fragment_upcoming_journeys.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_upcoming_journeys")
open class UpcomingJourneysFragment : HomeFragment() {

    private lateinit var vm: UpcomingJourneysViewModel

    private val journeysObserver = Observer<List<UpcomingJourney>> {
        if (!it.isNullOrEmpty()) {
            journeysAdapter.submitList(it)
        } else {
            //todo: show no journeys screen
        }
    }

    private val journeysAdapter =
        JourneysAdapter { findNavController().navigate(R.id.action_yourJourneysFragment__to_journeyDetailFragment_) }

    @AfterViews
    fun afterViews() {
        initToolbar()
        initRecyclerView()
        initViewModel()
    }

    private fun initViewModel(){
        vm = ViewModelProviders.of(this, viewModelFactory).get(UpcomingJourneysViewModel::class.java)
        vm.upcomingJourneys.observe(this, journeysObserver)
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_your_journeys))
        toolbar.startIconListener = { openDrawer() }
    }

    private fun initRecyclerView() {
        rc_view.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rc_view.adapter = journeysAdapter
    }
}