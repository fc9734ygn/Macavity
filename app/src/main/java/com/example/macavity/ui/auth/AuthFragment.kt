package com.example.macavity.ui.auth

import com.example.macavity.di.ViewModelFactory
import com.example.macavity.ui.base.BaseFragment
import javax.inject.Inject

open class AuthFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
}