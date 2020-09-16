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
        val dog3 = DogBreed("3", "buldog", "8-12 years", "fighters", "fight", "Savage", "")
        val dogsList = listOf<DogBreed>(dog1, dog2, dog3)

        //pass the information to mutable list
        dogs.value = dogsList
        dogsLoadError.value = false
        loading.value = false
    }

}