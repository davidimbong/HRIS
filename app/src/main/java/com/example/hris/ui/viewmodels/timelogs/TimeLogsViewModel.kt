package com.example.hris.ui.viewmodels.timelogs

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.R
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

    fun callTimeLogs() {
        viewModelScope.launch {
            loadingDialogState.value = true
            val call = hrisRepository.refreshTimeLogs()

            if (call != null) {
                if (call.isSuccess) {
                    timeLogs.value = call.timeLogs!!
                } else {
                    message.value = call.message!!
                }
            } else {
                message.value = getApplication<Application>().getString(R.string.network_error)
            }

            loadingDialogState.value = false
        }
    }
}