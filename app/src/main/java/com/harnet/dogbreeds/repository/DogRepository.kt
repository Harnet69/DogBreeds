package com.harnet.dogbreeds.repository

import com.harnet.dogbreeds.roomDb.DogBreed
import com.harnet.dogbreeds.roomDb.DogDAO
import com.harnet.dogbreeds.retrofit.DogsApiService
import io.reactivex.Single
import javax.inject.Inject

class DogRepository @Inject constructor(private val dogDAO: DogDAO, private val dogsApiService: DogsApiService): DogRepositoryInterface {

    override suspend fun insertAll(vararg dogs: DogBreed): List<Long> = dogDAO.insertAll(*dogs)

    override suspend fun getAllDogsFromAPI(): Single<List<DogBreed>> = dogsApiService.getDogs()

    override suspend fun getAllDogsFromDb(): List<DogBreed> = dogDAO.getAllDogs()

    override suspend fun getDog(id: String): DogBreed = dogDAO.getDogById(id)

    override suspend fun deleteAllDogs() { dogDAO.deleteAllDogs() }
}