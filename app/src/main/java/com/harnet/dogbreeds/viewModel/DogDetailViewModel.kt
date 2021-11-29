package com.harnet.dogbreeds.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harnet.dogbreeds.roomDb.DogBreed
import com.harnet.dogbreeds.repository.DogRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val repository: DogRepositoryInterface
) : ViewModel() {
    val dogLiveData = MutableLiveData<DogBreed>()

    //retrieve data from database by argument id
    fun fetch(dogId: String) {
        //get dog data from a database
        viewModelScope.launch {
            val dog = repository.getDog(dogId)
            dogLiveData.setValue(dog)
        }
    }
}