package com.example.hris.ui.viewmodels.timelogs

import androidx.lifecycle.ViewModel
import com.example.hris.convertTime24to12
import com.example.hris.timeNullChecker

class TimeLogsDetailedViewModel : ViewModel() {

    fun nullChecker(time: String?): String {
        if (time.isNullOrEmpty())
            return time.timeNullChecker()

        return time.convertTime24to12()
    }
}