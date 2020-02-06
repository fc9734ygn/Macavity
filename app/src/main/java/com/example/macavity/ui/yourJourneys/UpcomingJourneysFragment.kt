package com.example.macavity.ui.yourJourneys

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macavity.R
import com.example.macavity.data.models.Journey
import com.example.macavity.data.models.User
import com.example.macavity.ui.base.HomeFragment
import com.example.macavity.ui.calendar.JourneysAdapter
import kotlinx.android.synthetic.main.fragment_upcoming_journeys.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_upcoming_journeys")
open class UpcomingJourneysFragment : HomeFragment() {

    private lateinit var vm: UpcomingJourneysViewModel

    private val journeysAdapter =
        JourneysAdapter { findNavController().navigate(R.id.action_yourJourneysFragment__to_journeyDetailFragment_) }

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(UpcomingJourneysViewModel::class.java)
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_your_journeys))
        toolbar.startIconListener = { openDrawer() }
    }

    private fun initRecyclerView() {
        rc_view.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rc_view.adapter = journeysAdapter

        //todo: use real data
        val driver = User(
            "123",
            "John",
            "Kings Ave 22",
            "University of Kent",
            Pair(123.00, 123.00),
            Pair(23.2, 12.2),
            "https://media.gettyimages.com/photos/businessman-wearing-eyeglasses-picture-id825083358?b=1&k=6&m=825083358&s=612x612&w=0&h=SV2xnROuodWTh-sXycr-TULWi-bdlwBDXJkcfCz2lLc=",
            "asd",
            "asd",
            true,
            "asd",
            5
            , 2, 2
        )
        journeysAdapter.submitList(
            mutableListOf(
                Journey(
                    "123",
                    driver,
                    2,
                    listOf(driver),
                    1581004042000,
                    "will have to stop by the gas station, should take additional 10mins"
                ),
                Journey(
                    "123",
                    driver,
                    5,
                    listOf(driver),
                    1581004542000,
                    "will have to stop by the gas station, should take additional 10mins"
                ), Journey(
                    "123",
                    driver,
                    4,
                    listOf(driver),
                    1583004042000,
                    "will have to stop by the gas station, should take additional 10mins"
                ), Journey(
                    "123",
                    driver,
                    2,
                    listOf(driver),
                    1581022042000,
                    "will have to stop by the gas station, should take additional 10mins"
                ), Journey(
                    "123",
                    driver,
                    3,
                    listOf(driver),
                    1581004062000,
                    "will have to stop by the gas station, should take additional 10mins"
                ),
                Journey(
                    "123",
                    driver,
                    2,
                    listOf(driver),
                    1581004042200,
                    "will have to stop by the gas station, should take additional 10mins"
                )
            ).sortedBy { it.timeStamp }
        )
    }
}