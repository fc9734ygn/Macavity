package com.example.macavity.ui.splash

import androidx.lifecycle.ViewModelProviders
import com.example.macavity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity


@EActivity(resName = "activity_splash")
open class SplashActivity : BaseActivity() {

    private lateinit var vm: SplashViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory)[SplashViewModel::class.java]
    }

}
