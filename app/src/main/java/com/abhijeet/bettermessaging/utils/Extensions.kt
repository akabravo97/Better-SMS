package com.abhijeet.bettermessaging.utils

import java.text.SimpleDateFormat
import java.util.*


fun Long.formatTime(): String {
    val sdf = SimpleDateFormat("EEE, MMM d, ''yy")
    return sdf.format(Date(this))
}

fun String.containsOtp(): Boolean {
    return this.contains(" otp ", true)
}

fun String.findOtpLocation(): Pair<Int, Int> {
    val pattern = Regex("\\d\\d\\d\\d+")
    val match = pattern.find(this)
    if (match == null) return Pair(0, 0)
    return Pair(match!!.range.first, match!!.range.last)
}
