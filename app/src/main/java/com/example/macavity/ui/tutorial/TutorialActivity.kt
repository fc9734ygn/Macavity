package com.example.macavity.ui.tutorial

import androidx.lifecycle.ViewModelProviders
import com.example.macavity.ui.base.BaseActivity
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
