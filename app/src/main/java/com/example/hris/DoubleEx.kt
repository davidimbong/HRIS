package com.example.hris

fun Double.convertToNoDecimalString(): String {
    return if (this % 1.0 == 0.0) {
        String.format("%.0f", this)
    } else
        String.format("%s", this)
}