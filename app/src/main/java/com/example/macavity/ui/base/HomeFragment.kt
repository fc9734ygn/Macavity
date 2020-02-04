package com.example.macavity.ui.base

import com.example.macavity.ui.home.HomeActivity

open class HomeFragment : BaseFragment() {

    fun openDrawer() {
        (activity as HomeActivity).openDrawer()
    }
}