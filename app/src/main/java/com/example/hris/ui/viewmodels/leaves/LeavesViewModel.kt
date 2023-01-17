package com.example.hris.ui.viewmodels.leaves

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.convertToNoDecimalString
import com.example.hris.model.Leaves
import com.example.hris.model.TimeLogs
import com.example.hris.repository.HrisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeavesViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val user = hrisRepository.userData
    val leaves = MutableLiveData<List<Leaves>>()
    val loadingDialogState = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val totalVacationLeaves = MutableLiveData<Double>()
    val totalSickLeaves = MutableLiveData<Double>()

    fun callLeaves() {
        viewModelScope.launch {
            loadingDialogState.value = true
            val call = hrisRepository.refreshLeaves()

            if (call.isSuccess) {
                leaves.value = call.leaves
            } else {
                message.value = call.message!!
            }
            loadingDialogState.value = false
        }
        totalVacationLeaves.value = 13.0
        totalSickLeaves.value = 13.0
    }

    fun getLeavesLeft(double: Double): String{
        return (13.0 - double).convertToNoDecimalString()
    }
}
