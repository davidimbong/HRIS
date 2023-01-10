package com.example.hris.ui.viewmodels

import androidx.lifecycle.ViewModel

import com.google.type.DateTime
import java.time.Instant
import java.time.Instant.now
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AddTimelogSuccessViewModel : ViewModel() {

    fun getTime(): String {
        val formatter = DateTimeFormatter.ofPattern("h:mm a").withZone(ZoneId.of("Hongkong"))
//        val df = LocalDateTime.now()
//        df.atZone(ZoneId.of("UTC+08:00"))
        val time = formatter.format(Instant.now())

//        DateTime.now().toDateTime(DateTimeZone.UTC)

//        val test =
        return time.toString()
    }
}





