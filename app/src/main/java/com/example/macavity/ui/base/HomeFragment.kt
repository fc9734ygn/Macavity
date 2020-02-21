package com.example.macavity.ui.base

import com.example.macavity.di.ViewModelFactory
import com.example.macavity.ui.home.HomeActivity
import javax.inject.Inject

open class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    fun openDrawer() {
        (activity as HomeActivity).openDrawer()
    }
}