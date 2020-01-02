package com.example.macavity.ui.base

import android.content.Intent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.macavity.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    fun showError(message: String? = getString(R.string.default_error_message)) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun goToActivity(activity: BaseActivity){
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        setFullScreen()
    }

    private fun setFullScreen(){
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}