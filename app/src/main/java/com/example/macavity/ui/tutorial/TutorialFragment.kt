package com.example.macavity.ui.tutorial

import androidx.lifecycle.ViewModelProviders
import com.example.macavity.ui.base.AuthFragment
import com.example.macavity.ui.base.BaseActivity
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_tutorial")
open class TutorialFragment : AuthFragment() {

    lateinit var vm: TutorialViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(TutorialViewModel::class.java)
    }
}
