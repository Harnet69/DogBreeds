package com.harnet.dogbreeds.repository

import com.harnet.dogbreeds.model.DogBreed

interface DogRepositoryInterface {

    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    suspend fun getAllDogs(): List<DogBreed>

    suspend fun deleteAllDogs()

    suspend fun getDog(dogUuid: Int): DogBreed

    suspend fun getDog(id: String): DogBreed
}