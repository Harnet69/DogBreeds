package com.harnet.dogbreeds.retrofit

import com.harnet.dogbreeds.roomDb.DogBreed
import io.reactivex.Single
import retrofit2.http.GET

interface DogsAPI {
    // whole URL is https://raw.githubusercontent.com/DevTides/DogsApi/master/dogs.json

    // annotation used for knowing how this method can be used
    @GET("DevTides/DogsApi/master/dogs.json")// !!!this is an end point, not all url
    fun getDogs():Single<List<DogBreed>>

    // it can be several methods for different kinds of a data
}