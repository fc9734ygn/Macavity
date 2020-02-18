package com.example.macavity.utils

import android.util.Patterns

fun isValidEmail(target: CharSequence?): Boolean {
    return if (target == null) false else Patterns.EMAIL_ADDRESS.matcher(target).matches()
}

fun isValidPhoneNumber(target: CharSequence?): Boolean{
    return if (target == null) false else Patterns.PHONE.matcher(target).matches()
}