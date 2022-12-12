package com.example.hris.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hris.model.User

@Dao
interface HrisDao {

    @Query("SELECT * from profile_data")
    fun getProfile(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(locations: User)

    @Query("DELETE from profile_data")
    fun deleteAll()
}

@Database(entities = [User::class], version = 1)
abstract class HrisDatabase: RoomDatabase() {
    abstract fun hrisDao(): HrisDao
}