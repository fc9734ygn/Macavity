package com.example.macavity.ui.base

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.macavity.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    fun showError(message: String = getString(R.string.default_error_message)){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }
}