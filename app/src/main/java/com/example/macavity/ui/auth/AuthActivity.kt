package com.example.macavity.ui.auth

import com.example.macavity.ui.base.BaseActivity
import org.androidannotations.annotations.EActivity

@EActivity(resName = "activity_auth")
open class AuthActivity : BaseActivity() {
    lateinit var vm: AuthViewModel
}