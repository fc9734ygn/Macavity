package com.example.macavity.ui.splash

import android.os.Bundle
import com.example.macavity.R
import com.example.macavity.ui.base.BaseActivity
import org.androidannotations.annotations.EActivity

@EActivity(resName = "activity_splash")
open class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_splash)
    }
}
