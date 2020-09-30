package com.harnet.dogbreeds.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harnet.dogbreeds.model.DogBreed

class DetailViewModel: ViewModel() {
    val dogLiveData = MutableLiveData<DogBreed>()

    //retrieve data from database by agrument's id
    //TODO there will be id as argument for retrieveng dog data from a database
    fun fetch(dogId: String, dogName: String, dogLifeSpan: String, dogBreedGroup: String, dogBredFor: String, dogTemperament: String, dogImagURL: String){
    // attach mock dog to dogLiveData
        val dog = DogBreed(dogId, dogName, dogLifeSpan, dogBreedGroup, dogBredFor, dogTemperament, dogImagURL)
        dogLiveData.value = dog
        println(dog)
    }
}