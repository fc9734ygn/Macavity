package com.example.macavity.ui

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EViewGroup

@EViewGroup(resName = "toolbar")
open class Toolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppBarLayout(context, attrs) {

    var startIconListener: () -> Unit = {}
    var endIconListener: () -> Unit = {}

    @Click(resName = ["startIcon"])
    fun onStartIconClick() {
        startIconListener.invoke()
    }

    @Click(resName = ["endIcon"])
    fun onEndIconClick() {
        endIconListener.invoke()
    }
}