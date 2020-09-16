package com.harnet.dogbreeds.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harnet.dogbreeds.model.DogBreed

class DetailViewModel: ViewModel() {
    val dogLiveData = MutableLiveData<DogBreed>()

    //retrieve data from database by agrument's id
    //TODO there will be id as argument for retrieveng dog data from a database
    fun fetch(){
    // attach mock dog to dogLiveData
        val dog = DogBreed("1", "pug", "12-15 years", "puggy", "sofa", "Wonderful", "")
        dogLiveData.value = dog
    }
}