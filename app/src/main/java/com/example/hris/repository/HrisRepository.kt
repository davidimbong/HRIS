package com.example.hris.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hris.data.HrisDao
import com.example.hris.model.*
import com.example.hris.network.HrisApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HrisRepository @Inject constructor(
    private val hrisDao: HrisDao
) {

    val loginData = MutableLiveData<User>()
    val loginMessage = MutableLiveData<String>()

    val userData = hrisDao.getProfile()

    val timeLogs: LiveData<List<TimeLogs>> = hrisDao.getTimeLogs()

    suspend fun insertProfile(username: String, password: String) {
        val call = HrisApi.retrofitService.getProfile(
            username,
            password
        )

        if (call.status == "0") {
            withContext(Dispatchers.IO) {
                hrisDao.deleteProfile()
                hrisDao.insertProfile(call.user!!)
                loginData.postValue(hrisDao.getProfile().value)
            }
        } else {
            loginMessage.postValue(call.message!!)
        }
    }

    suspend fun updateProfile(
        firstName: String,
        middleName: String?,
        lastName: String,
        emailAddress: String,
        mobileNumber: String,
        landline: String?
    ): String {
        val call = HrisApi.retrofitService.updateProfile(
            userData.value!!.userID,
            firstName,
            middleName,
            lastName,
            emailAddress,
            mobileNumber,
            landline
        )

        if (call.status == "0") {
            hrisDao.deleteProfile()
            hrisDao.insertProfile(
                User(
                    userData.value!!.userID,
                    userData.value!!.idNumber,
                    firstName,
                    middleName,
                    lastName,
                    emailAddress,
                    mobileNumber,
                    landline
                )
            )
        }

        return call.message!!
    }

    suspend fun refreshTimeLogs():TimeLogsModel=
        withContext(Dispatchers.IO) {
            val call = HrisApi.retrofitService.getTimeLogs(
                userData.value!!.userID
            )
            if (call.status == "0") {
                hrisDao.deleteTimeLogs()
                hrisDao.insertTimeLogs(call.timeLogs!!)
            }
            call
        }

    suspend fun addTimeLogs(type: String): ResponseModel =
        withContext(Dispatchers.IO) {
            HrisApi.retrofitService.addTimeLogs(
                userData.value!!.userID,
                type
            )
        }
}