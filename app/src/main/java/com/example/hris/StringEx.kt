package com.example.hris

import java.math.RoundingMode
import java.text.DecimalFormat


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
        if (index >= 8 && index <= 10) {
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
