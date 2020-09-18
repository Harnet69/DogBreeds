package com.harnet.dogbreeds.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogsApiService {
    // base URL of the API
    private val BASE_URL = "https://raw.githubusercontent.com"
    // object created by Retrofit for accessing to an endpoint
    private val api =  Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())// converts JSON to object of our class
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// convert this object to observable Single<List<>>
        .build()
        .create(DogsAPI::class.java)// create model class

    //get observable List from API
    fun getDogs(): Single<List<DogBreed>>{
        return api.getDogs()
    }

}