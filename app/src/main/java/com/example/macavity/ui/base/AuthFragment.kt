package com.example.macavity.ui.base

import com.example.macavity.di.ViewModelFactory
import javax.inject.Inject

open class AuthFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
}