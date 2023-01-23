package com.example.hris.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hris.model.Leaves
import com.example.hris.model.TimeLogs
import com.example.hris.model.User

@Dao
interface HrisDao {

    @Query("SELECT * from profile_data")
    fun getProfile(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(user: User)

    @Update
    fun updateProfile(user: User)

    @Query("DELETE from profile_data")
    fun deleteProfile()

    @Query("SELECT * from time_logs")
    fun getTimeLogs(): LiveData<List<TimeLogs>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTimeLogs(timeLogs: List<TimeLogs>)

    @Update
    fun updateTimeLogs(timeLogs: List<TimeLogs>)

    @Query("DELETE from time_logs")
    fun deleteTimeLogs()

    @Query("SELECT * from leaves")
    fun getLeaves(): LiveData<List<Leaves>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLeaves(leaves: List<Leaves>)

    @Query("DELETE from leaves")
    fun deleteLeaves()
}

@Database(
    entities = [User::class, TimeLogs::class, Leaves::class],
    version = 2
)
abstract class HrisDatabase : RoomDatabase() {
    abstract fun hrisDao(): HrisDao
}