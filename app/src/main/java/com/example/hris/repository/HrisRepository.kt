package com.example.hris.repository

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
    val userData = hrisDao.getProfile()
    val timeLogs = hrisDao.getTimeLogs()

    suspend fun login(username: String, password: String): LoginModel {
        val call = HrisApi.retrofitService.getProfile(
            username,
            password
        )

        if (call.isSuccess) {
            withContext(Dispatchers.IO) {
                hrisDao.deleteProfile()
                hrisDao.insertProfile(call.user!!)
            }
        }
        return call
    }

    suspend fun updateProfile(
        firstName: String,
        middleName: String?,
        lastName: String,
        emailAddress: String,
        mobileNumber: String,
        landline: String?
    ): ResponseModel {
        return withContext(Dispatchers.IO) {
            val call = HrisApi.retrofitService.updateProfile(
                userData.value!!.userID,
                firstName,
                middleName,
                lastName,
                emailAddress,
                mobileNumber,
                landline
            )

            if (call.isSuccess) {
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

            call
        }
    }

    suspend fun refreshTimeLogs(): TimeLogsModel =
        withContext(Dispatchers.IO) {
            val call = HrisApi.retrofitService.getTimeLogs(
                userData.value!!.userID
            )
            if (call.isSuccess) {
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

    suspend fun refreshLeaves(): LeavesModel =
        withContext(Dispatchers.IO) {
            val call = HrisApi.retrofitService.getLeaves(
                userData.value!!.userID
            )
            if (call.isSuccess) {
                hrisDao.deleteLeaves()
                hrisDao.insertLeaves(call.leaves)
            }
            call
        }

    suspend fun fileLeave(
        type: String,
        time: String,
        dateFrom: String,
        dateTo: String?
    ): ResponseModel =
        withContext(Dispatchers.IO) {
            HrisApi.retrofitService.fileLeave(
                userData.value!!.userID,
                type,
                dateFrom,
                dateTo,
                time
            )
        }
}