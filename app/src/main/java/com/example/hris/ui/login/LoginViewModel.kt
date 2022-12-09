package com.example.hris.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hris.model.LoginRequestBody
import com.example.hris.network.HrisApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    fun userLogin(username: String, password: String){
        if(username.isNotEmpty() && password.isNotEmpty()){
            viewModelScope.launch {
                Log.d("ASD", "Launched $username & $password")
                getLoginDetails(username, password)
                Log.d("ASD", "Finished")
            }
        }
    }

    private suspend fun getLoginDetails(username: String, password: String) {
        Log.d("ASD", "Calling")
        val call = HrisApi.retrofitService.getLogin(
            LoginRequestBody(
                username,
                password
            )
        )

        Log.d("ASD", call.toString())

    }
}