package com.harnet.dogbreeds.repository

import com.harnet.dogbreeds.model.DogBreed
import io.reactivex.Single

interface DogRepositoryInterface {

    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    suspend fun getAllDogsFromAPI(): Single<List<DogBreed>>

    suspend fun getAllDogsFromDb(): List<DogBreed>

    suspend fun deleteAllDogs()

    suspend fun getDog(dogUuid: Int): DogBreed

    suspend fun getDog(id: String): DogBreed


}