package com.example.hris.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hris.convertToLandline
import com.example.hris.convertToPhone
import com.example.hris.hideEmail
import com.example.hris.hidePhoneNumber
import com.example.hris.model.Profile
import com.example.hris.repository.HrisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    hrisRepository: HrisRepository,
    application: Application
) : AndroidViewModel(application) {

    private val userData = hrisRepository.profileData
    val userProfile = MutableLiveData<Profile>()

    fun getProfile() {
        val initials = "${userData.value!!.firstName.first()}${userData.value!!.lastName.first()}"
        var name = ""
        userData.value?.apply {
            name = "${this.firstName.uppercase()} " +
                    if (!this.middleName.isNullOrEmpty()) "${this.middleName.uppercase()} "
                    else "" +
                            this.lastName.uppercase()
        }
        val idNumber = userData.value!!.idNumber
        val email = userData.value!!.emailAddress.hideEmail()
        val phoneNumber = userData.value!!.mobileNumber.convertToPhone().hidePhoneNumber()

        userProfile.value = Profile(initials, name, idNumber, email, phoneNumber)
    }
}