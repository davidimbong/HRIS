package com.example.hris.ui.viewmodels.leaves

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.getNumberOfDaysInBetween
import com.example.hris.model.Leaves
import com.example.hris.repository.HrisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeavesViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val leaves = MutableLiveData<List<Leaves>>()
    val loadingDialogState = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val totalVacationLeavesLeft = MutableLiveData<Double>()
    val totalSickLeavesLeft = MutableLiveData<Double>()

    fun callLeaves() {
        viewModelScope.launch {
            loadingDialogState.value = true
            val call = hrisRepository.refreshLeaves()

            if (call.isSuccess) {
                leaves.value = call.leaves
                setTotalLeavesLeft()
            } else {
                message.value = call.message!!
            }
            loadingDialogState.value = false
        }
    }

    private fun setTotalLeavesLeft() {
        var totalVacationLeaves = 13.0
        var totalSickLeaves = 13.0
        leaves.value!!.forEach {
            val days: Double = if (it.dateTo != null) {
                it.dateFrom.getNumberOfDaysInBetween(it.dateTo)
            } else {
                0.5
            }
            if (it.isVacationLeave()) {
                totalVacationLeaves -= days
            } else {
                totalSickLeaves -= days
            }
        }

        totalVacationLeavesLeft.value = totalVacationLeaves
        totalSickLeavesLeft.value = totalSickLeaves
    }
}
