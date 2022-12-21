package com.example.hris.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.model.TimeLogs
import com.example.hris.network.HrisApi
import com.example.hris.repository.HrisRepository
import com.example.hris.ui.DialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeLogsViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val user = hrisRepository.profileData
    val timeLogs = MutableLiveData<List<TimeLogs>>()
    val loadingDialogState = MutableLiveData<DialogState>()
    val message = MutableLiveData<String>()

    fun getTimeLogs(userId: String) {
        viewModelScope.launch {
            loadingDialogState.value = DialogState.SHOW
            callTimeLogs(userId)
            loadingDialogState.value = DialogState.HIDE
        }
    }

    private suspend fun callTimeLogs(userId: String) {
        val call = HrisApi.retrofitService.getTimeLogs(
            userId
        )

        if (call.status == "0") {
            timeLogs.value = call.timeLogs!!
            hrisRepository.refreshTimeLogs(timeLogs.value!!)
        } else {
            message.value = call.message!!
        }
    }
}