package com.example.hris.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.model.ResponseModel
import com.example.hris.network.HrisApi
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

    fun addTimeLogs(type: String) {
        viewModelScope.launch {
            callApi(type)
        }
    }

    private suspend fun callApi(type: String) {
        loadingDialogState.value = true
        val call = HrisApi.retrofitService.addTimeLogs(
            hrisRepository.profileData.value!!.userID,
            type
        )
        callValue.value = call
        loadingDialogState.value = false
    }
}