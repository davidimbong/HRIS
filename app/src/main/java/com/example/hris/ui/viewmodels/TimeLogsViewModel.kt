package com.example.hris.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.model.TimeLogs
import com.example.hris.repository.HrisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeLogsViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val user = hrisRepository.userData
    val timeLogs = MutableLiveData<List<TimeLogs>>()
    val loadingDialogState = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val timeLogsResponse = hrisRepository.timeLogsResponse

    fun callTimeLogs() {
        viewModelScope.launch {
            loadingDialogState.value = true
            hrisRepository.refreshTimeLogs()
        }
    }

    fun getTimeLogs(){
        if (timeLogsResponse.value!!.status == "0") {
            timeLogs.value = timeLogsResponse.value!!.timeLogs!!
        } else {
            message.value = timeLogsResponse.value!!.message!!
        }
        loadingDialogState.value = false
    }
}