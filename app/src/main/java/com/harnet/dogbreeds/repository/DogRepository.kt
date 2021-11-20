package com.harnet.dogbreeds.repository

import com.harnet.dogbreeds.model.DogBreed
import com.harnet.dogbreeds.model.DogDAO
import javax.inject.Inject

class DogRepository @Inject constructor(private val dogDAO: DogDAO): DogRepositoryInterface {

    override suspend fun insertAll(vararg dogs: DogBreed): List<Long> = dogDAO.insertAll(*dogs)

    override suspend fun getAllDogs(): List<DogBreed> = dogDAO.getAllDogs()

    override suspend fun deleteAllDogs() { dogDAO.deleteAllDogs() }

    override suspend fun getDog(dogUuid: Int): DogBreed = dogDAO.getDog(dogUuid)

    override suspend fun getDog(id: String): DogBreed = dogDAO.getDog(id)

    //TODO implement Retrofit functionality here
}