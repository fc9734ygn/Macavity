package com.example.macavity.ui.map

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.example.macavity.R
import com.example.macavity.ui.base.BaseFragment
import com.example.macavity.ui.home.HomeActivity
import com.example.macavity.ui.home.HomeActivity_
import kotlinx.android.synthetic.main.fragment_map.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_map")
open class MapFragment : BaseFragment() {

    private lateinit var vm: MapViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(MapViewModel::class.java)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_map))

        toolbar.startIconListener = { openDrawer() }
    }
}
