package com.example.hris.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hris.model.User

@Dao
interface HrisDao {

    @Query("SELECT * from user_login")
    fun getLocations(): LiveData<User>

    @Query("SELECT * from user_login WHERE userID = :userID")
    fun getLocation(userID: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLogins(locations: User)

    @Query("DELETE from user_login")
    fun deleteAll()
}

@Database(entities = [User::class], version = 1)
abstract class HrisDatabase: RoomDatabase() {
    abstract fun hrisDao(): HrisDao
}