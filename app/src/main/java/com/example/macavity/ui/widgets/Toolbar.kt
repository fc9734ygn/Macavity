package com.example.macavity.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.example.macavity.utils.increaseClickAreaForMultipleViews
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.toolbar.view.*
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EViewGroup

@EViewGroup(resName = "toolbar")
open class Toolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppBarLayout(context, attrs) {

    var startIconListener: () -> Unit = {}
    var endIconListener: () -> Unit = {}

    fun setTitle(text: String): Toolbar {
        title.text = text
        title.visibility = View.VISIBLE
        return this
    }

    fun setStartIcon(resId: Int): Toolbar {
        startIcon.setImageDrawable(resources.getDrawable(resId, null))
        startIcon.visibility = View.VISIBLE
        increaseClickAreas()
        return this
    }

    fun setEndIcon(resId: Int): Toolbar {
        endIcon.setImageDrawable(resources.getDrawable(resId, null))
        endIcon.visibility = View.VISIBLE
        increaseClickAreas()
        return this
    }

    private fun increaseClickAreas() {
        val hardToClickViews = listOf(startIcon, endIcon)
        increaseClickAreaForMultipleViews(container, hardToClickViews)
    }

    @Click(resName = ["startIcon"])
    fun onStartIconClick() {
        startIconListener.invoke()
    }

    @Click(resName = ["endIcon"])
    fun onEndIconClick() {
        endIconListener.invoke()
    }
}