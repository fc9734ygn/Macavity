package com.example.macavity.ui.calendar

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.example.macavity.R

import com.example.macavity.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_map.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_calendar")
open class CalendarFragment : BaseFragment() {

    private lateinit var vm: CalendarViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_calendar))

        toolbar.startIconListener = { openDrawer() }
    }
}