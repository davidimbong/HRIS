package com.example.hris.ui.profile

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
import retrofit2.http.Field
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val userData = hrisRepository.profileData
    val loadingDialogState = MutableLiveData<DialogState>()
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
            loadingDialogState.value = DialogState.SHOW
            update(firstName, middleName, lastName, emailAddress, mobileNumber, landline)
            loadingDialogState.value = DialogState.HIDE
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
        val call = HrisApi.retrofitService.updateProfile(
            userID, firstName, middleName, lastName, emailAddress, mobileNumber, landline
        )

        if (call.status == "0") {
            hrisRepository.refreshRepository(
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

            message.value = call.message
        } else {
            loadingDialogState.value = DialogState.ERROR
        }
    }
}