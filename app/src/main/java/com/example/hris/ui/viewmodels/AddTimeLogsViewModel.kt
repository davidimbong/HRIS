package com.example.hris.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.model.ResponseModel
import com.example.hris.repository.HrisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddTimeLogsViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val loadingDialogState = MutableLiveData<Boolean>()
    val callValue = MutableLiveData<ResponseModel>()

    fun addTimeLogs(type: String) {
        viewModelScope.launch {
            getResponse(type)
        }
    }

    suspend fun getResponse(type: String) {
        viewModelScope.async {
            loadingDialogState.value = true
            hrisRepository.addTimeLogs(
                type
            )
            loadingDialogState.value = false
            callValue.value = hrisRepository.addTimeLogsResponse.value
        }.await()
    }
}