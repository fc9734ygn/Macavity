package com.example.macavity.ui.addJourney

import androidx.lifecycle.ViewModelProviders
import com.example.macavity.R
import com.example.macavity.data.models.User

import com.example.macavity.ui.base.HomeFragment
import com.example.macavity.ui.widgets.TimePickerFragment
import kotlinx.android.synthetic.main.fragment_add_journey.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment
import kotlin.math.min

@EFragment(resName = "fragment_add_journey")
open class AddJourneyFragment : HomeFragment() {

    private lateinit var vm: AddJourneyViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(AddJourneyViewModel::class.java)
        initToolbar()
        //todo: use real data
        val user = User(
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
        setInitialData(user)
    }

    private fun setInitialData(user: User) {
        //TODO: use real data
        date.text = "05 Jan"
        free_seats.setText(user.carFreeSeats.toString())
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_arrow_back).setEndIcon(R.drawable.ic_add)
            .setTitle("Create journey")
        toolbar.startIconListener = { requireActivity().onBackPressed() }
        //todo: add journey
        toolbar.endIconListener = { requireActivity().onBackPressed() }
    }

    @Click(resName = ["time"])
    fun showTimePicker() {
        TimePickerFragment { hours, minutes ->
            setTime(
                hours,
                minutes
            )
        }.show(requireFragmentManager(), "timePicker")
    }

    private fun setTime(hours: Int, minutes: Int) {
        val hoursFormatted = if (hours.toString().length <= 1) "0$hours" else hours.toString()
        val minutesFormatted = if(minutes.toString().length <= 1) "0$minutes" else minutes.toString()
        time.text = String.format(getString(R.string.add_journey_time), hoursFormatted, minutesFormatted)
    }
}
