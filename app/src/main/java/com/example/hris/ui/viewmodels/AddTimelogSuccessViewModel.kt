package com.example.hris.ui.viewmodels

import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddTimelogSuccessViewModel : ViewModel() {

    fun getTime(): String {
        val formatter = DateTimeFormatter.ofPattern("h:mm a")
        return LocalDateTime.now().format(formatter)
    }
}





