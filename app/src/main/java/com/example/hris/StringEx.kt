package com.example.hris

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*


fun String.convertToPhone(): String {
    val sb = StringBuilder()
    sb.append("+63 ")
    this.forEachIndexed { index, c ->
        if (index == 0) {
            //skip
        } else {
            sb.append(c)
            if (index % 3 == 0 && this.count() - index > 3) {
                sb.append(" ")
            }
        }
    }

    return sb.toString()
}

fun String.convertToLocalPhone(): String {
    val list = this.split(' ')
    val sb = StringBuilder()
    this.removeRange(0, 2)
    sb.append("0")
    list.forEach {
        sb.append(it)
    }

    return sb.toString()
}

fun String.convertToLandline(): String {
    val sb = StringBuilder()
    sb.append("+63 ")
    this.forEachIndexed { index, c ->
        if ((index + 1) % 3 == 0 && this.count() - index > 3) {
            sb.append(" ")
        }
        sb.append(c)
    }

    return sb.toString()
}

fun String.hidePhoneNumber(): String {
    val sb = StringBuilder()
    this.forEachIndexed { index, c ->
        if (index in 8..10) {
            sb.append("*")
        } else {
            sb.append(c)
        }
    }

    return sb.toString()
}

fun String.hideEmail(): String {
    val list = this.split('@')
    val sb = StringBuilder()
    list[0].forEachIndexed { index, c ->
        if (index < 3) {
            sb.append(c)
        }
    }
    sb.append("***")
    sb.append("@${list[1]}")

    return sb.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.convertDateMonthDay(): String {
    val list = this.split('/')
    val sb = StringBuilder()
    sb.append(DateFormatSymbols().months[list[0].toInt() - 1])
    sb.append(" ")
    sb.append(list[1].toInt().toString())
    return sb.toString()
}

fun String.convertTime24to12(): String {
    val list = this.split(':')
    val sb = StringBuilder()
    sb.append(list[0].toInt().toString())
    sb.append(":")
    sb.append(list[1])
    val _24HFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val _12HFormatter = SimpleDateFormat("h:mm a", Locale.getDefault())
    val time = _24HFormatter.parse(sb.toString())
    return _12HFormatter.format(time!!).toString()
}

fun String?.timeNullChecker(): String {
    if (this.isNullOrEmpty()) {
        return "N/A"
    }
    return this
}

fun String?.textColorSetter(): Int {
    if (this.isNullOrEmpty()) {
        return Color.parseColor("#880808")
    }
    return Color.parseColor("#000000")
}