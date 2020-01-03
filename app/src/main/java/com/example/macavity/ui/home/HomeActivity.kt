package com.example.macavity.ui.main

import com.example.macavity.R
import com.example.macavity.ui.base.BaseActivity
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity


@EActivity(resName = "activity_home")
open class MainActivity : BaseActivity() {

    @AfterViews
    fun afterViews() {
        initToolbar()
    }

    private fun initToolbar() {
        toolbar
            .setTitle(getString(R.string.toolbar_title_home))
            .setStartIcon(R.drawable.ic_menu)

        toolbar.startIconListener = { onBackPressed() }
    }

    @Click(resName = ["calendar_button"])
    fun goToCalendar(){
        //TODO: goto calendar
    }

    @Click(resName = ["chat_button"])
    fun goToChat(){
        //TODO: goto chat
    }

}
