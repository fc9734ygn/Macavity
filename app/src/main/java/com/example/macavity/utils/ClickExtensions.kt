package com.example.macavity.utils

import android.graphics.Rect
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View

const val DEFAULT_CLICK_AREA_INCREASE_IN_DP = 24

/**
 * Function to increase the click area around a view.
 *
 * ALL PARAMETERS TAKE VALUES IN DP
 *
 * Default value 24dp for each side
 *
 * Doesn't work when using for multiple views in the same parent view use [increaseClickAreaForMultipleViews]
 *
 * @param top amount to increase on top of the view
 * @param bottom amount to increase bottom of the view
 * @param start amount to increase left from the view
 * @param end amount to increase right of the view
 */
fun View.increaseClickArea(
    top: Int = DEFAULT_CLICK_AREA_INCREASE_IN_DP,
    bottom: Int = DEFAULT_CLICK_AREA_INCREASE_IN_DP,
    start: Int = DEFAULT_CLICK_AREA_INCREASE_IN_DP,
    end: Int = DEFAULT_CLICK_AREA_INCREASE_IN_DP
) {
    val parent = parent as View
    parent.post {
        val rectangle = Rect()
        getHitRect(rectangle)
        rectangle.top -= top.toPx()
        rectangle.bottom += bottom.toPx()
        rectangle.left -= start.toPx()
        rectangle.right += end.toPx()
        parent.touchDelegate = TouchDelegate(rectangle, this)
    }
}


/**
 * Function to increase click area for multiple hardToClickViews in same parent.
 */
fun increaseClickAreaForMultipleViews(parentView: View,
                                      hardToClickViews: List<View>,
                                      top: Int = DEFAULT_CLICK_AREA_INCREASE_IN_DP,
                                      bottom: Int = DEFAULT_CLICK_AREA_INCREASE_IN_DP,
                                      start: Int = DEFAULT_CLICK_AREA_INCREASE_IN_DP,
                                      end: Int = DEFAULT_CLICK_AREA_INCREASE_IN_DP) {
    parentView.post {
        val delegateComposite = TouchDelegateComposite(parentView)
        for (view in hardToClickViews) {
            val rectangle = Rect()
            view.getHitRect(rectangle)
            rectangle.top -= top.toPx()
            rectangle.bottom += bottom.toPx()
            rectangle.left -= start.toPx()
            rectangle.right += end.toPx()
            delegateComposite.addDelegate(TouchDelegate(rectangle, view))
        }
        parentView.touchDelegate = delegateComposite
    }
}

class TouchDelegateComposite(view: View) : TouchDelegate(Rect(), view) {
    private val delegates = ArrayList<TouchDelegate>()

    fun addDelegate(delegate: TouchDelegate?) {
        if (delegate != null) {
            delegates.add(delegate)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var res = false
        val x = event.x
        val y = event.y
        for (delegate in delegates) {
            event.setLocation(x, y)
            res = delegate.onTouchEvent(event) || res
        }
        return res
    }
}