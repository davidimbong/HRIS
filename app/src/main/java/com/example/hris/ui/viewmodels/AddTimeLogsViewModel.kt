package com.example.hris.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.model.AddTimeLogs
import com.example.hris.network.HrisApi
import com.example.hris.ui.DialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTimeLogsViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    val loadingDialogState = MutableLiveData<DialogState>()
    val callValue = MutableLiveData<AddTimeLogs>()

    fun addTimeLogs(userId: String, type: String) {
        viewModelScope.launch {
            loadingDialogState.value = DialogState.SHOW
            callApi(userId, type)
        }
    }

    private suspend fun callApi(userId: String, type: String) {
        val call = HrisApi.retrofitService.addTimeLogs(
            userId,
            type
        )
        callValue.value = call
        loadingDialogState.value = DialogState.HIDE
    }
}