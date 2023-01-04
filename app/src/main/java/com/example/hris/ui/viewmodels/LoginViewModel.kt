package com.example.hris.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.model.User
import com.example.hris.network.HrisApi
import com.example.hris.repository.HrisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val userData = MutableLiveData<User>()

    val loadingDialogState = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun userLogin(username: String, password: String) {
        viewModelScope.launch {
            getLoginDetails(username, password)
        }
    }

    private suspend fun getLoginDetails(username: String, password: String) {
        loadingDialogState.value = true
        val call = HrisApi.retrofitService.getProfile(
            username,
            password
        )

        if (call.status == "0") {
            userData.value = call.user!!
            hrisRepository.refreshProfile(userData.value!!)
        } else {
            message.value = call.message!!
        }
        loadingDialogState.value = false
    }
}