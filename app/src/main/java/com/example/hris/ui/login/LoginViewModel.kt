package com.example.hris.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.model.User
import com.example.hris.network.HrisApi
import com.example.hris.repository.HrisRepository
import com.example.hris.ui.DialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val userData = MutableLiveData<User>()
    val loadingDialogState = MutableLiveData<DialogState>()
    val message = MutableLiveData<String>()

    fun userLogin(username: String, password: String) {
        viewModelScope.launch {
            loadingDialogState.value = DialogState.SHOW
            getLoginDetails(username, password)
            loadingDialogState.value = DialogState.HIDE
        }
    }

    private suspend fun getLoginDetails(username: String, password: String) {
        val call = HrisApi.retrofitService.getProfile(
            username,
            password
        )

        if (call.status.equals("0")) {
            userData.value = call.user!!
            hrisRepository.refreshRepository(userData.value!!)
        } else {
            call.message
            loadingDialogState.value = DialogState.ERROR
        }
    }
}