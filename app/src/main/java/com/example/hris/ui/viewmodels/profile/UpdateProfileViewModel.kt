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
    val isSuccessful = MutableLiveData<Unit>()

    fun updateProfile(
        firstName: String,
        middleName: String?,
        lastName: String,
        emailAddress: String,
        mobileNumber: String,
        landline: String?,
        context: Context
    ) {
        if (areInputsValid(
                firstName = firstName,
                middleName = middleName,
                lastName = lastName,
                emailAddress = emailAddress,
                mobileNumber = mobileNumber,
                landline = landline,
                context = context
            )
        ) {
            viewModelScope.launch {
                loadingDialogState.value = true
                val call = hrisRepository.updateProfile(
                    firstName = firstName,
                    middleName = middleName,
                    lastName = lastName,
                    emailAddress = emailAddress,
                    mobileNumber = mobileNumber,
                    landline = landline,
                )

                if (call.isSuccess) {
                    isSuccessful.value = Unit
                } else {
                    message.value = call.message!!
                }
                loadingDialogState.value = false
            }
        }
    }

    private fun areInputsValid(
        firstName: String,
        middleName: String?,
        lastName: String,
        emailAddress: String,
        mobileNumber: String,
        landline: String?,
        context: Context
    ): Boolean {
        if (!Regex("^([A-Za-z\\s]+$)").matches(firstName)) {
            message.value = context.getString(R.string.first_name_error)
        } else if (!Regex("^([A-Za-z\\s]+$)").matches(middleName!!)) {
            message.value = context.getString(R.string.middle_name_error)
        } else if (!Regex("^([A-Za-z\\s]+$)").matches(lastName)) {
            message.value = context.getString(R.string.last_name_error)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            message.value = context.getString(R.string.email_address_error)
        } else if (!Regex("^(09[0-9]{9}$)").matches(mobileNumber)) {
            message.value = context.getString(R.string.mobile_number_error)
        } else if (!Regex("^(0[0-9]{9}$)").matches(landline!!)) {
            message.value = context.getString(R.string.landline_number_error)
        } else {
            return true
        }
        return false
    }
}