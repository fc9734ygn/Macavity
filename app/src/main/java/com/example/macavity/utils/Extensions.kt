package com.example.macavity.utils

import android.content.res.Resources

/**
 * Extension which is used to convert pixels to device-independent pixels
 */
fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Extension which is used to convert device-independent pixels to pixels
 */
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()