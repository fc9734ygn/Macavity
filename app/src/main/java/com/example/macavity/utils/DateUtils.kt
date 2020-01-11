package com.example.macavity.utils

import java.util.*

fun timestampToDate(timestamp: Long): Date {
    return Date(timestamp * 1000)
}