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

    val profileData: LiveData<User> = hrisDao.getProfile()

    val timeLogs: LiveData<List<TimeLogs>> = hrisDao.getTimeLogs()
    val timeLogsResponse = MutableLiveData<TimeLogsModel>()
    val addTimeLogsResponse = MutableLiveData<AddTimeLogsResponseModel>()

    suspend fun refreshProfile(user: User) {
        withContext(Dispatchers.IO) {
            hrisDao.deleteProfile()
            hrisDao.insertProfile(user)
        }
    }

    suspend fun refreshTimeLogs() {
        withContext(Dispatchers.IO) {
            val call = HrisApi.retrofitService.getTimeLogs(
                profileData.value!!.userID
            )
            if (call.status == "0") {
                hrisDao.deleteTimeLogs()
                hrisDao.insertTimeLogs(call.timeLogs!!)
            }
            timeLogsResponse.postValue(call)
        }
    }

    suspend fun addTimeLogs(type: String) {
        withContext(Dispatchers.IO) {
            val call = HrisApi.retrofitService.addTimeLogs(
                profileData.value!!.userID,
                type
            )
            addTimeLogsResponse.postValue(call)
        }
    }
}