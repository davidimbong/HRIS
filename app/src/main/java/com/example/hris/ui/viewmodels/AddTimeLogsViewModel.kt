package com.example.hris.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.model.AddTimeLogsResponseModel
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
    val callValue = hrisRepository.addTimeLogsResponse

    fun addTimeLogs(type: String) {
        viewModelScope.launch {
            loadingDialogState.value = true
            hrisRepository.addTimeLogs(
                type
            )
            loadingDialogState.value = false
        }
    }

    fun resetValue(){
        hrisRepository.addTimeLogsResponse.value = null
    }

}