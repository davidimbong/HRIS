package com.example.hris.di

import android.content.Context
import com.example.hris.ui.BaseApplication
import com.example.hris.ui.CustomDialogFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Provides
    fun provideCustomDialog(): CustomDialogFragment {
        return CustomDialogFragment()
    }
}