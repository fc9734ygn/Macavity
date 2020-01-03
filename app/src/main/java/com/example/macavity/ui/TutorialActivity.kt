package com.example.macavity.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.macavity.R
import com.example.macavity.ui.base.BaseActivity
import com.example.macavity.ui.home.HomeViewModel
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity

@EActivity(resName = "activity_tutorial")
open class TutorialActivity : BaseActivity() {

    lateinit var vm: TutorialViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory)[TutorialViewModel::class.java]
    }
}
