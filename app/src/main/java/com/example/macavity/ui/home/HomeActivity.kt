package com.example.macavity.ui.home

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.macavity.R
import com.example.macavity.ui.base.BaseActivity
import com.example.macavity.ui.signIn.SignInViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity


@EActivity(resName = "activity_home")
open class HomeActivity : BaseActivity() {

    lateinit var vm: HomeViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]
    }

    @Click(resName = ["calendar_button"])
    fun goToCalendar(){
    }

    @Click(resName = ["chat_button"])
    fun goToChat(){
    }
    
    @Click(resName = ["fab"])
    fun onFabClick(){
    }
}
