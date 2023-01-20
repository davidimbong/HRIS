package com.example.hris.ui.viewmodels.profile

import android.app.Application
import android.content.Context
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hris.R
import com.example.hris.repository.HrisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    val userData = hrisRepository.userData
    val loadingDialogState = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val isSuccessfull = MutableLiveData<Unit>()

    fun updateProfile(
        firstName: String,
        middleName: String?,
        lastName: String,
        emailAddress: String,
        mobileNumber: String,
        landline: String?,
        context: Context
    ) {
        if (!nameIsValid(firstName)) {
            message.value = context.getString(R.string.first_name_error)
        } else if (!middleNameIsValid(middleName)) {
            message.value = context.getString(R.string.middle_name_error)
        } else if (!nameIsValid(lastName)) {
            message.value = context.getString(R.string.last_name_error)
        } else if (!emailIsValid(emailAddress)) {
            message.value = context.getString(R.string.email_address_error)
        } else if (!mobileNumberIsValid(mobileNumber)) {
            message.value = context.getString(R.string.mobile_number_error)
        } else if (!landlineNumberIsValid(landline)) {
            message.value = context.getString(R.string.landline_number_error)
        } else {
            viewModelScope.launch {
                loadingDialogState.value = true
                val call = hrisRepository.updateProfile(
                    firstName,
                    middleName,
                    lastName,
                    emailAddress,
                    mobileNumber,
                    landline
                )

                if (call.isSuccess) {
                    isSuccessfull.value = Unit
                } else {
                    message.value = call.message!!
                }
                loadingDialogState.value = false
            }
        }
    }

    private fun nameIsValid(name: String): Boolean {
        return Regex("^([A-Za-z\\s]+$)").matches(name)
    }

    private fun middleNameIsValid(name: String?): Boolean {
        return if (name.isNullOrEmpty()) {
            true
        } else {
            Regex("^([A-Za-z\\s]+$)").matches(name)
        }
    }

    private fun emailIsValid(emailAddress: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
    }

    private fun mobileNumberIsValid(mobileNumber: String): Boolean {
        return Regex("^(09[0-9]{9}$)").matches(mobileNumber)
    }

    private fun landlineNumberIsValid(landline: String?): Boolean {
        return if (landline.isNullOrEmpty()) {
            true
        } else {
            Regex("^(0[0-9]{10}$)").matches(landline)
        }
    }
}