package com.harnet.dogbreeds.retrofit

import com.harnet.dogbreeds.roomDb.DogBreed
import io.reactivex.Single
import javax.inject.Inject

class DogsApiService @Inject constructor( private val api: DogsAPI) {
    //get observable List from API
    fun getDogs(): Single<List<DogBreed>>{
        return api.getDogs()
    }

}