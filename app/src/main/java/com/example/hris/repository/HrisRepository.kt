package com.example.hris.repository

import androidx.lifecycle.LiveData
import com.example.hris.data.HrisDao
import com.example.hris.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HrisRepository @Inject constructor(
    private val hrisDao: HrisDao
){

    val profileData: LiveData<User> = hrisDao.getProfile()


    suspend fun refreshRepository(user: User) {
        withContext(Dispatchers.IO) {
            hrisDao.deleteAll()
            hrisDao.insertProfile(user)
        }
    }
}