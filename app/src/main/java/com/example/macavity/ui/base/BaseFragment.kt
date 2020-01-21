package com.example.macavity.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.macavity.R
import com.example.macavity.ui.home.HomeActivity
import dagger.android.DaggerFragment


open class BaseFragment : dagger.android.support.DaggerFragment() {

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    fun showError(message:String?){
        (activity as BaseActivity).showError(message)
    }
}