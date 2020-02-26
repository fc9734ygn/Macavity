package com.example.macavity.utils

import java.util.*
import java.util.concurrent.TimeUnit

fun millisecondsToDate(millis: Long): Date {
    return Date(millis * 1000)
}

fun millisecondsToSeconds(milliseconds: Long): Long {
    return TimeUnit.MILLISECONDS.toSeconds(milliseconds)
}

fun secondsToMillis(seconds: Long): Long{
    return seconds * 1000
}