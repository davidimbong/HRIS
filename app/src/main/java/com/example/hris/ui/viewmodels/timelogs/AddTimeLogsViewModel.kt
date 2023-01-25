package com.example.hris.ui.viewmodels.timelogs

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.R
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
    val liveDataSuccess = MutableLiveData<Unit>()
    val message = MutableLiveData<String>()

    fun addTimeLogs(type: String, context: Context) {
        viewModelScope.launch {
            loadingDialogState.value = true
            val call = hrisRepository.addTimeLogs(
                type
            )
            loadingDialogState.value = false
            if (call != null) {
                if (call.isSuccess) {
                    liveDataSuccess.value = Unit
                } else {
                    message.value = call.message!!
                }
            } else {
                message.value = getApplication<Application>().getString(R.string.network_error)
            }
        }
    }
}