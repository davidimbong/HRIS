package com.example.hris.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.R
import com.example.hris.network.SimpleResponse
import com.example.hris.repository.HrisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val liveDataSuccess = MutableLiveData<Unit>()
    val loadingDialogState = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun userLogin(username: String, password: String) {
        viewModelScope.launch {
            loadingDialogState.value = true
            val call = hrisRepository.login(username = username, password = password)

            if (call != null) {
                if (call.isSuccess) {
                    liveDataSuccess.value = Unit
                } else {
                    message.value = call.message!!
                }
            } else {
                message.value = getApplication<Application>().getString(R.string.network_error)
            }

            loadingDialogState.value = false

        }
    }
}