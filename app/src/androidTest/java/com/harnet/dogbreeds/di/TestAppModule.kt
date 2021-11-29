package com.harnet.dogbreeds.di

import android.content.Context
import androidx.room.Room
import com.harnet.dogbreeds.roomDb.DogsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@HiltAndroidTest
object TestAppModule {

    @Singleton
    @Provides
    @Named("dogDatabase")
    fun provideInMemoryRoomDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, DogsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}