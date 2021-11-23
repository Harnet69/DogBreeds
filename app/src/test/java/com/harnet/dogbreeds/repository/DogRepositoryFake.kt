package com.harnet.dogbreeds.repository

import androidx.lifecycle.MutableLiveData
import com.harnet.dogbreeds.model.DogBreed
import io.reactivex.Single

class DogRepositoryFake: DogRepositoryInterface {
    private val dogsList = arrayListOf<DogBreed>()
    private val dogListLiveData = MutableLiveData<List<DogBreed>>(dogsList)

    override suspend fun insertAll(vararg dogs: DogBreed): List<Long> {
        dogsList.addAll(dogs)
        refreshData()
        return listOf(0L)
    }

    override suspend fun getAllDogsFromAPI(): Single<List<DogBreed>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllDogsFromDb(): List<DogBreed> = dogsList

    override suspend fun deleteAllDogs() { dogsList.clear() }

    override suspend fun getDog(dogUuid: Int): DogBreed = dogsList.first { it.uuid == dogUuid }

    override suspend fun getDog(id: String): DogBreed = dogsList.first { it.breedId == id }

    // fill live data with a predefine data
    private fun refreshData(){
        dogListLiveData.postValue(dogsList)
    }
}