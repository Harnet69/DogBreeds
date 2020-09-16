package com.harnet.dogbreeds.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harnet.dogbreeds.model.DogBreed

class ListViewModel: ViewModel() {
    val dogs = MutableLiveData<List<DogBreed>>()// provide an info of actual list of dogs what we retrieve from data source
    val dogsLoadError = MutableLiveData<Boolean>()//notify about generic error with data's retrieval because it listens the ViewModel
    val loading = MutableLiveData<Boolean>()// listening is data is loading

    //refresh information from sources
    //TODO update this method after implementing backend API retrieval service
    fun refresh(){
        //some mock data for testing
        val dog1 = DogBreed("1", "pug", "12-15 years", "puggy", "sofa", "Wonderful", "")
        val dog2 = DogBreed("2", "pudel", "13 years", "wolf", "hunting", "Energetic", "")
        val dog3 = DogBreed("3", "bul", "8-12 years", "fighters", "fight", "Savage", "")
        val dog4 = DogBreed("4", "buld", "8-12 years", "fighters", "fight", "Savage", "")
        val dog5 = DogBreed("5", "dog", "8-12 years", "fighters", "fight", "Savage", "")
        val dog6 = DogBreed("6", "uldog", "8-12 years", "fighters", "fight", "Savage", "")
        val dog7 = DogBreed("7", "og", "8-12 years", "fighters", "fight", "Savage", "")
        val dog8 = DogBreed("8", "uld", "8-12 years", "fighters", "fight", "Savage", "")
        val dogsList = listOf<DogBreed>(dog1, dog2, dog3, dog4, dog5, dog6, dog7, dog8)

        //pass the information to mutable list
        dogs.value = dogsList
        dogsLoadError.value = false
        loading.value = false
    }

}