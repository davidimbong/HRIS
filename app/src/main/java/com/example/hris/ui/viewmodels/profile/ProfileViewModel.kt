package com.example.hris.ui.viewmodels.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.example.hris.convertToInternationalLandlineNumber
import com.example.hris.convertToInternationalPhoneNumber
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

    private val userData = hrisRepository.userData
    val userProfile = MediatorLiveData<Profile>()


    init {
        userProfile.addSource(userData) {

            val initials = "${it.firstName.first()}${it.lastName.first()}"
            val name = "${it.firstName.uppercase()} " +
                    if (!it.middleName.isNullOrEmpty()) "${it.middleName.uppercase()} "
                    else "" +
                            it.lastName.uppercase()
            val idNumber = it.idNumber
            val email = it.emailAddress.hideEmail()
            val phoneNumber = it.mobileNumber.convertToInternationalPhoneNumber().hidePhoneNumber()

            userProfile.value = Profile(initials, name, idNumber, email, phoneNumber)
        }
    }
}