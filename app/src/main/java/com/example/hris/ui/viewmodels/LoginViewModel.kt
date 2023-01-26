package com.example.hris.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.R
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

    fun userLogin(username: String, password: String, context: Context) {
        viewModelScope.launch {
            loadingDialogState.value = true
            }

            loadingDialogState.value = false

//            loadingDialogState.value = true
//            try {
//            val call = hrisRepository.login(username = username, password = password)
//            if (call.body.isSuccess) {
//                liveDataSuccess.value = Unit
//            } else {
//                message.value = call.body.message!!
//            }
//            loadingDialogState.value = false
//            } catch (networkError: IOException) {
//                message.value = context.getString(R.string.network_error)
//                loadingDialogState.value = false
//            }
//            catch (networkError: TimeoutException){
//                message.value = context.getString(R.string.timeout_error)
//                loadingDialogState.value = false
//            }
        }
    }
}