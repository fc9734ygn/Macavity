package com.example.macavity.ui.calendar

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

import com.example.macavity.ui.base.BaseFragment
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_calendar")
open class CalendarFragment : BaseFragment() {

    private lateinit var vm: CalendarViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
