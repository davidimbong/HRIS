package com.example.hris.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.model.LoginModel
import com.example.hris.repository.HrisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val liveDataSuccess = MutableLiveData<Unit>()
    val loginResponse = MutableLiveData<LoginModel>()
    val loadingDialogState = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun userLogin(username: String, password: String) {
        viewModelScope.launch {
            loadingDialogState.value = true
            loginResponse.value = hrisRepository.login(username, password)
            loadingDialogState.value = false
        }
    }

    fun isValidLogin() {
        if (loginResponse.value!!.status == "0") {
            viewModelScope.launch {
                hrisRepository.refreshProfile(loginResponse.value!!.user!!)
                liveDataSuccess.value = Unit
            }
        } else {
            message.value = loginResponse.value!!.message!!
        }
    }
}