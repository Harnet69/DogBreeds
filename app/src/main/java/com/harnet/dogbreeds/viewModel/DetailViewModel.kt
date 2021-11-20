package com.harnet.dogbreeds.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.harnet.dogbreeds.model.DogBreed
import com.harnet.dogbreeds.model.DogsDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(application: Application) : BaseViewModel(application) {
    val dogLiveData = MutableLiveData<DogBreed>()

    //retrieve data from database by argument id
    fun fetch(dogId: String) {
        //get dog data from a database
        launch {
            val dog = DogsDatabase.invoke(getApplication()).dogDAO().getDog(dogId)
            dogLiveData.setValue(dog)
        }
    }
}