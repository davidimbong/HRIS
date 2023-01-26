package com.example.hris.ui.viewmodels.leaves

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.R
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
    val totalVacationLeavesLeft = MediatorLiveData<Double>()
    val totalSickLeavesLeft = MediatorLiveData<Double>()

    init {
        totalVacationLeavesLeft.addSource(leaves) {
            totalVacationLeavesLeft.value =
                13.0 - it.filter { leave ->
                    leave.isVacationLeave()
                }.sumOf { leave ->
                    leave.getDaysInBetween()
                }
        }

        totalSickLeavesLeft.addSource(leaves) {
            totalSickLeavesLeft.value =
                13.0 - it.filter { leave ->
                    !leave.isVacationLeave()
                }.sumOf { leave ->
                    leave.getDaysInBetween()
                }
        }
    }

    fun callLeaves() {
        viewModelScope.launch {
            loadingDialogState.value = true
            val call = hrisRepository.refreshLeaves()

            if (call != null) {
                if (call.isSuccess) {
                    leaves.value = call.leaves
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
