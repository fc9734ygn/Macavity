package com.example.macavity.ui.base

import am.appwise.components.ni.NoInternetDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.macavity.R
import dagger.android.support.DaggerAppCompatActivity
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import javax.inject.Inject


open class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var noInternetDialog: NoInternetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNoInternetDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        noInternetDialog.onDestroy()
    }

    private fun initNoInternetDialog(){
        noInternetDialog = NoInternetDialog.Builder(this)
            .setBgGradientStart(getColor(R.color.blue_light))
            .setBgGradientCenter(getColor(R.color.blue_primary))
            .setBgGradientEnd(getColor(R.color.blue_dark))
            .setButtonColor(getColor(R.color.amber_primary))
            .build()
    }

    fun toast(message: String? = getString(R.string.default_error_message)) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun setFullScreen(){
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}