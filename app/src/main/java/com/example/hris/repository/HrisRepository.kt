package com.example.hris.repository

import com.example.hris.data.HrisDao
import com.example.hris.model.*
import com.example.hris.network.HrisApi
import com.example.hris.network.SimpleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HrisRepository @Inject constructor(
    private val hrisDao: HrisDao
) {
    val userData = hrisDao.getProfile()
    val timeLogs = hrisDao.getTimeLogs()

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): T? {
        val call = try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }

        if (call.failed || !call.isSuccesful) {
            return null
        }

        return call.body
    }

    suspend fun login(username: String, password: String): LoginModel? {
//        val call = HrisApi.retrofitService.getProfile(
//            username,
//            password
//        )
//
//        if (call.isSuccess) {
//            withContext(Dispatchers.IO) {
//                hrisDao.updateProfile(call.user!!)
//            }
//        }
//        return call


        return safeApiCall {
            HrisApi.retrofitService.getProfile(
                userID = username,
                password = password
            )
        }
    }

    suspend fun updateProfile(
        firstName: String,
        middleName: String?,
        lastName: String,
        emailAddress: String,
        mobileNumber: String,
        landline: String?
    ): ResponseModel? {
        return withContext(Dispatchers.IO) {
            val call = safeApiCall {
                HrisApi.retrofitService.updateProfile(
                    userData.value!!.userID,
                    firstName,
                    middleName,
                    lastName,
                    emailAddress,
                    mobileNumber,
                    landline
                )
            }

            if (call != null) {
                if (call.isSuccess) {
                    hrisDao.updateProfile(
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
            }
            call
        }
    }

    suspend fun refreshTimeLogs(): TimeLogsModel? =
        withContext(Dispatchers.IO) {
            val call = safeApiCall {
                HrisApi.retrofitService.getTimeLogs(
                    userData.value!!.userID
                )
            }

            if (call?.isSuccess == true) {
                hrisDao.updateTimeLogs(call.timeLogs!!)
            }
            call
        }

//    suspend fun refreshTimeLogs(): TimeLogsModel =
//        withContext(Dispatchers.IO) {
//            val call = HrisApi.retrofitService.getTimeLogs(
//                userData.value!!.userID
//            )
//            if (call.isSuccess) {
//                hrisDao.updateTimeLogs(call.timeLogs!!)
//            }
//            call
//        }

    suspend fun addTimeLogs(type: String): ResponseModel? =
        withContext(Dispatchers.IO) {
            safeApiCall {
                HrisApi.retrofitService.addTimeLogs(
                    userData.value!!.userID,
                    type
                )
            }
        }


//    suspend fun addTimeLogs(type: String): ResponseModel =
//        withContext(Dispatchers.IO) {
//            HrisApi.retrofitService.addTimeLogs(
//                userData.value!!.userID,
//                type
//            )
//        }

    suspend fun refreshLeaves(): LeavesModel? =
        withContext(Dispatchers.IO) {
            val call = safeApiCall {
                HrisApi.retrofitService.getLeaves(
                    userData.value!!.userID
                )
            }
            if (call != null) {
                if (call.isSuccess) {
                    hrisDao.updateLeaves(call.leaves)
                }
            }
            call
        }

    suspend fun fileLeave(
        type: String,
        time: String,
        dateFrom: String,
        dateTo: String?
    ): ResponseModel? =
        withContext(Dispatchers.IO) {
            safeApiCall {
                HrisApi.retrofitService.fileLeave(
                    userData.value!!.userID,
                    type,
                    dateFrom,
                    dateTo,
                    time
                )
            }
        }
}