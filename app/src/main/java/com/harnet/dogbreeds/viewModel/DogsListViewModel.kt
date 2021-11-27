package com.harnet.dogbreeds.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harnet.dogbreeds.di.TAG
import com.harnet.dogbreeds.model.DogBreed
import com.harnet.dogbreeds.repository.DogRepositoryInterface
import com.harnet.dogbreeds.util.NotificationsHelper
import com.harnet.dogbreeds.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsListViewModel @Inject constructor(
    private val repository: DogRepositoryInterface
) : ViewModel() {

    // allows observe observable Single<List<DogBreed>>, handle retrieving and avoid memory leaks
    // (can be produced because of waiting for observable while app has been destroyed)
    private val disposable = CompositeDisposable()

    // provide an info of actual list of dogs what we retrieve from data source
    val mDogs = MutableLiveData<List<DogBreed>>()

    //notify about generic error with data's retrieval because it listens the ViewModel
    val mDogsLoadError = MutableLiveData<Boolean>()

    // listening is data loading
    val mLoading = MutableLiveData<Boolean>()

    //refresh information from remote API
    fun refreshFromAPI(context: Context) {
        fetchFromRemoteWithRetrofit(context)
    }

    //refresh information from a database
    fun refreshFromDatabase() {
        fetchFromDatabase()
    }

    // fetches data with Retrofit library from remote API
    private fun fetchFromRemoteWithRetrofit(context: Context) {
        //set loading flag to true
        mLoading.value = true

        viewModelScope.launch {
            disposable.add(
                // set it to a different thread(passing this call to the background thread)
                repository.getAllDogsFromAPI()
                    .subscribeOn(Schedulers.newThread())
                    // retrieve it from a background to the main thread for displaying
                    .observeOn(AndroidSchedulers.mainThread())
                    // pass our Single object here, it's created and implemented
                    .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>() {
                        // get list of DogBreed objects
                        override fun onSuccess(dogsList: List<DogBreed>) {
                            //store this information and time of retrieving in a db as a cache
                            Log.i(TAG, "onSuccess: ")
                            storeDogInDatabase(dogsList)
                            SharedPreferencesHelper.invoke(context)
                                .saveTimeOfUpd(System.nanoTime())
                            Toast.makeText(
                                context,
                                "Get data from API",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            // notification
                            NotificationsHelper(context).createNotification()
                        }

                        // get an error
                        override fun onError(e: Throwable) {
                            mDogsLoadError.value = true
                            mLoading.value = false
                            // print stack of error to handling it
                            e.printStackTrace()
                        }
                    })
            )
        }
    }

    // retrieve dogs and set UI components. Can work separately from DB
    private fun retrieveDogs(dogsList: List<DogBreed>) {
        // set received list to observable mutable list
        mDogs.postValue(dogsList)
        // switch off error message
        mDogsLoadError.postValue(false)
        // switch off waiting spinner
        mLoading.postValue(false)
    }

    // initiate and handle data in database
    fun storeDogInDatabase(dogsList: List<DogBreed>) {
        //launch code in separate thread in Coroutine scope
        viewModelScope.launch {
            repository.deleteAllDogs()
            // argument is an expanded list of individual elements
            val result = repository.insertAll(*dogsList.toTypedArray())
            // update receiver list with assigning uuId to the right objects
            for (i in dogsList.indices) {
                dogsList[i].uuid = result[i].toInt()
            }
            retrieveDogs(dogsList)
        }
    }

    // get data from dogs database
    private fun fetchFromDatabase() {
        viewModelScope.launch {
            retrieveDogs(repository.getAllDogsFromDb())
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}