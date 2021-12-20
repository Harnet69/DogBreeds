package com.harnet.dogbreeds.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.harnet.dogbreeds.R
import com.harnet.dogbreeds.roomDb.DogDAO
import com.harnet.dogbreeds.roomDb.DogsDatabase
import com.harnet.dogbreeds.retrofit.DogsAPI
import com.harnet.dogbreeds.retrofit.DogsApiService
import com.harnet.dogbreeds.repository.DogRepository
import com.harnet.dogbreeds.repository.DogRepositoryInterface
import com.harnet.dogbreeds.util.BASE_URL
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DogsDatabase::class.java, "dogDatabase").build()

    @Singleton
    @Provides
    fun provideDogDAO(database: DogsDatabase) = database.dogDAO()
    //TODO inject repository with this

    @Singleton
    @Provides
    fun provideDogRepository(dao: DogDAO, dogsApiService: DogsApiService) =
        DogRepository(dao, dogsApiService) as DogRepositoryInterface

    @Singleton
    @Provides
    fun provideDogsAPI(): DogsAPI {
        // OkHttp interceptor which logs HTTP request and response data
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            // handle all basic communication, separate threads, errors and converts JSON to object of our class
            .addConverterFactory(GsonConverterFactory.create())
            // convert this object to observable Single<List<>>
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DogsAPI::class.java)// create model class

    }

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_img_default)
                .error(R.drawable.ic_launcher_foreground)
        )
}