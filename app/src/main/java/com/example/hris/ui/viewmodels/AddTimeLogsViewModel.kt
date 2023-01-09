package com.example.hris.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.model.ResponseModel
import com.example.hris.repository.HrisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTimeLogsViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val loadingDialogState = MutableLiveData<Boolean>()
    val callValue = MutableLiveData<ResponseModel>()
    val message = MutableLiveData<String>()

    fun addTimeLogs(type: String) {
        viewModelScope.launch {
            loadingDialogState.value = true
            val call = hrisRepository.addTimeLogs(
                type
            )
            loadingDialogState.value = false

            if (call.status == "0") {
                callValue.value = call
            } else {
                message.value = call.message!!
            }
        }
    }
}