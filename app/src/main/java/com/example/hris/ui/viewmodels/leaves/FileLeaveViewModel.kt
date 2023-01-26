package com.example.hris.ui.viewmodels.leaves

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.R
import com.example.hris.repository.HrisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileLeaveViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val loadingDialogState = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val isSuccess = MutableLiveData<Unit>()
    var type = ""

    fun fileLeave(time: Int, dateFrom: String?, dateTo: String?) {
        if (checkIfValid(time, dateFrom, dateTo)) {
            viewModelScope.launch {
                loadingDialogState.value = true
                val call = hrisRepository.fileLeave(
                    type = type,
                    time = time.toString(),
                    dateFrom = dateFrom!!,
                    dateTo = dateTo
                )

                if (call != null) {
                    if (call.isSuccess) {
                        isSuccess.value = Unit
                    } else {
                        message.value = call.message!!
                    }
                    loadingDialogState.value = false
                } else {
                    message.value = getApplication<Application>().getString(R.string.network_error)
                }
            }
        }
    }

    private fun checkIfValid(time: Int, dateFrom: String?, dateTo: String?): Boolean {
        return if (time == 1 && (dateFrom.isNullOrEmpty() && dateTo.isNullOrEmpty())) {
            message.value = "Please select a start and end date"
            false

        } else if ((time == 2 || time == 3) && dateFrom.isNullOrEmpty()) {
            message.value = "Please select a date"
            false

        } else if (type.isEmpty()) {
            message.value = "Please select a leave type"
            false

        } else {
            true
        }
    }
}