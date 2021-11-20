package com.harnet.dogbreeds.di

import android.content.Context
import androidx.room.Room
import com.harnet.dogbreeds.model.DogDAO
import com.harnet.dogbreeds.model.DogDatabase
import com.harnet.dogbreeds.repository.DogRepository
import com.harnet.dogbreeds.repository.DogRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogBreedsAppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DogDatabase::class.java, "dogDatabase").build()

    @Singleton
    @Provides
    fun provideDogDAO(database: DogDatabase) = database.dogDAO()
    //TODO inject repository with this

    @Singleton
    @Provides
    fun provideDogRepository(dao: DogDAO) = DogRepository(dao) as DogRepositoryInterface


}