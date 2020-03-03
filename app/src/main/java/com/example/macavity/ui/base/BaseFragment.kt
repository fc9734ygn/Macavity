package com.example.macavity.ui.base

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation


open class BaseFragment : dagger.android.support.DaggerFragment() {

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    fun toast(message:String? = null){
        (activity as BaseActivity).toast(message)
    }
}