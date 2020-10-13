package com.harnet.dogbreeds.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.harnet.dogbreeds.model.DogBreed
import com.harnet.dogbreeds.model.DogDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    val dogLiveData = MutableLiveData<DogBreed>()

    //retrieve data from database by agrument id
    fun fetch(dogId: String) {
        //get dog data from a database
        launch {
            val dog = DogDatabase.invoke(getApplication()).dogDAO().getDog(dogId)
            dogLiveData.setValue(dog)
        }
    }
}