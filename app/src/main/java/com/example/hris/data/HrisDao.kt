package com.example.hris.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hris.model.TimeLogs
import com.example.hris.model.User

@Dao
interface HrisDao {

    @Query("SELECT * from profile_data")
    fun getProfile(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(user: User)

    @Query("DELETE from profile_data")
    fun deleteProfile()

    @Query("SELECT * from time_logs")
    fun getTimeLogs(): LiveData<List<TimeLogs>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTimeLogs(timeLogs: List<TimeLogs>)

    @Query("DELETE from time_logs")
    fun deleteTimeLogs()
}

@Database(entities = [User::class, TimeLogs::class], version = 1)
abstract class HrisDatabase: RoomDatabase() {
    abstract fun hrisDao(): HrisDao
}