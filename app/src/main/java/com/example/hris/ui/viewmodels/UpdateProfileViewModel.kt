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
class UpdateProfileViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val userData = hrisRepository.profileData
    val loadingDialogState = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun updateProfile(
        firstName: String,
        middleName: String?,
        lastName: String,
        emailAddress: String,
        mobileNumber: String,
        landline: String?
    ) {
        viewModelScope.launch {
            update(firstName, middleName, lastName, emailAddress, mobileNumber, landline)
        }
    }

    private suspend fun update(
        firstName: String,
        middleName: String?,
        lastName: String,
        emailAddress: String,
        mobileNumber: String,
        landline: String?
    ) {
        val userID = userData.value!!.userID
        val idNumber = userData.value!!.idNumber
        loadingDialogState.value = true
        val call = HrisApi.retrofitService.updateProfile(
            userID, firstName, middleName, lastName, emailAddress, mobileNumber, landline
        )

        if (call.status == "0") {
            hrisRepository.refreshProfile(
                User(
                    userID,
                    idNumber,
                    firstName,
                    middleName,
                    lastName,
                    emailAddress,
                    mobileNumber,
                    landline
                )
            )
        } else {
            message.value = call.message
        }
        loadingDialogState.value = false
    }
}