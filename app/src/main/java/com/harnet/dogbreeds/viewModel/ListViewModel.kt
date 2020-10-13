package com.harnet.dogbreeds.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.harnet.dogbreeds.model.DogBreed
import com.harnet.dogbreeds.model.DogDatabase
import com.harnet.dogbreeds.model.DogsApiService
import com.harnet.dogbreeds.util.OwnDataParser
import com.harnet.dogbreeds.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture

class ListViewModel(application: Application) : BaseViewModel(application) {
    // instantiate DogsApiService
    private val dogsApiService = DogsApiService()

    // allows observe observable Single<List<DogBreed>>, handle retrieving and avoid memory leaks
    // (can be produced because of waiting for observable while app has been destroyed)
    private val disposable = CompositeDisposable()

    // provide an info of actual list of dogs what we retrieve from data source
    val mDogs = MutableLiveData<List<DogBreed>>()

    //notify about generic error with data's retrieval because it listens the ViewModel
    val mDogsLoadError = MutableLiveData<Boolean>()

    // listening is data loading
    val mLoading = MutableLiveData<Boolean>()

    //TODO make a switcher for switch between two approaches of API fetching

    //refresh information from remote API
    fun refreshFromAPI() {
        fetchFromRemoteWithRetrofit()
//        fetchFromRemoteWithOwnParser()
        Toast.makeText(getApplication(), "API data", Toast.LENGTH_SHORT).show()
    }

    //refresh information from a database
    fun refreshFromDatabase() {
        fetchFromDatabase()
//        fetchFromRemoteWithOwnParser()
        Toast.makeText(getApplication(), "Database data", Toast.LENGTH_SHORT).show()
    }

    // fetches data with OWN API PARSER from remote API
    private fun fetchFromRemoteWithOwnParser() {
        Log.i("Coroutiness", "fetchFromRemoteWithOwnParser: ")
        //TODO !!!DON'T FORGET TO ADD INTERNET PERMISSION BEFORE IMPLEMENTING!!!
        val ownDataParser = OwnDataParser()
        // set loading flag to true
        mLoading.value = true

        CompletableFuture.supplyAsync { ownDataParser.parseDataFromJSONStr() }
            .thenAccept { dogsList ->
                storeDogInDatabase(dogsList)
//                retrieveDogs(dogsList)
            }
    }

    // fetches data with Retrofit library from remote API
    private fun fetchFromRemoteWithRetrofit() {
        //TODO !!!DON'T FORGET TO ADD INTERNET PERMISSION BEFORE IMPLEMENTING!!!
        //set loading flag to true
        mLoading.value = true

        disposable.add(
            // set it to a different thread(passing this call to the background thread)
            dogsApiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                // retrieve it from a background to the main thread for displaying
                .observeOn(AndroidSchedulers.mainThread())
                // pass our Single object here, it's created and implemented
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>() {
                    // get list of DogBreed objects
                    override fun onSuccess(dogsList: List<DogBreed>) {
                        //TODO store this information and time of retrieving in a db as a cache
                        storeDogInDatabase(dogsList)
                        SharedPreferencesHelper.invoke(getApplication()).saveTimeOfUpd(System.nanoTime())
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
            val dao = DogDatabase(getApplication()).dogDAO()
            dao.deleteAllDogs()
            // argument is an expanded list of individual elements
            val result = dao.insertAll(*dogsList.toTypedArray())
            // update receiver list with assigning uuId to the right objects
            //TODO can be a cause of a problem, because of for loop instead while
            for (i in dogsList.indices) {
                dogsList[i].uuid = result[i].toInt()
            }
            retrieveDogs(dogsList)
        }
    }

    // get data from dogs database
    private fun fetchFromDatabase(){
        launch {
            val dogsFromDb = DogDatabase.invoke(getApplication()).dogDAO().getAllDogs()
            retrieveDogs(dogsFromDb)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}