package com.example.hris.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.repository.HrisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val userData = hrisRepository.loginData

    val loadingDialogState = MutableLiveData<Boolean>()
    val message = hrisRepository.loginMessage

    fun userLogin(username: String, password: String) {
        viewModelScope.launch {
            loadingDialogState.value = true
            hrisRepository.insertProfile(username, password)
            loadingDialogState.value = false
        }
    }
}