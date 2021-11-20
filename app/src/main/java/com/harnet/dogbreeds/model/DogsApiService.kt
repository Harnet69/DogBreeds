package com.harnet.dogbreeds.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class DogsApiService @Inject constructor() {
    // base URL of the API
    private val BASE_URL = "https://raw.githubusercontent.com"
    // object created by Retrofit for accessing to an endpoint
    private val api =  Retrofit.Builder()
        .baseUrl(BASE_URL)
        // handle all basic communication, separate threads, errors and converts JSON to object of our class
        .addConverterFactory(GsonConverterFactory.create())
        // convert this object to observable Single<List<>>
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(DogsAPI::class.java)// create model class

    //get observable List from API
    fun getDogs(): Single<List<DogBreed>>{
        return api.getDogs()
    }

}