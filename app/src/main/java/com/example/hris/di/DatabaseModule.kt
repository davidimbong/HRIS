package com.example.hris.di

import android.content.Context
import androidx.room.Room
import com.example.hris.data.HrisDao
import com.example.hris.data.HrisDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideLocatorDao(database: HrisDatabase): HrisDao {
        return database.hrisDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): HrisDatabase {
        return Room.databaseBuilder(
            appContext,
            HrisDatabase::class.java,
            "hris_database"
        ).build()
    }
}