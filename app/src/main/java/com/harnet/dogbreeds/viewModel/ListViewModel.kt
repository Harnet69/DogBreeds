package com.harnet.dogbreeds.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
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
class ListViewModel @Inject constructor(
    application: Application,
    private val repository: DogRepositoryInterface
) : BaseViewModel(application) {

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
    fun refreshFromAPI() {
        fetchFromRemoteWithRetrofit()
    }

    //refresh information from a database
    fun refreshFromDatabase() {
        fetchFromDatabase()
    }

    // fetches data with Retrofit library from remote API
    private fun fetchFromRemoteWithRetrofit() {
        //set loading flag to true
        mLoading.value = true

        launch {
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
                            storeDogInDatabase(dogsList)
                            SharedPreferencesHelper.invoke(getApplication())
                                .saveTimeOfUpd(System.nanoTime())
                            Toast.makeText(
                                getApplication(),
                                "Get data from API",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            // notification
                            NotificationsHelper(getApplication()).createNotification()
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
    private fun storeDogInDatabase(dogsList: List<DogBreed>) {
        //launch code in separate thread in Coroutine scope
        launch {
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
        launch {
            val dogsFromDb = repository.getAllDogsFromDb()
            retrieveDogs(dogsFromDb)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}