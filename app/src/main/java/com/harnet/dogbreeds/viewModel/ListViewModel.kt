package com.harnet.dogbreeds.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harnet.dogbreeds.model.DogBreed
import com.harnet.dogbreeds.model.DogsApiService
import com.harnet.dogbreeds.util.OwnDataDownloader
import com.harnet.dogbreeds.util.OwnDataParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.CompletableFuture

class ListViewModel : ViewModel() {
    // instantiate DogsApiService
    private val dogsService = DogsApiService()

    // allows observe observable Single<List<DogBreed>>, handle retrieving and avoid memory leaks
    // (can be produced because of waiting for observable while app has been destroyed)
    private val disposable = CompositeDisposable()

    // provide an info of actual list of dogs what we retrieve from data source
    val dogs = MutableLiveData<List<DogBreed>>()

    //notify about generic error with data's retrieval because it listens the ViewModel
    val dogsLoadError = MutableLiveData<Boolean>()

    // listening is data loading
    val loading = MutableLiveData<Boolean>()

    //TODO make a switcher for switch between two approaches of API fetching

    //refresh information from remote or local sources
    fun refresh() {
        fetchFromRemoteWithRetrofit()
//        fetchFromRemoteWithOwnParser()
    }

    // fetches data with OWN API PARSER from remote API
    private fun fetchFromRemoteWithOwnParser() {
        //TODO !!!DON'T FORGET TO ADD INTERNET PERMISSION BEFORE IMPLEMENTING!!!
        val ownDataParser = OwnDataParser()
        // set loading flag to true
        loading.value = true

        CompletableFuture.supplyAsync { ownDataParser.parseDataFromJSONStr() }
            .thenAccept {dogsFromAPI ->
                // set received list to observable mutable list
                dogs.postValue(dogsFromAPI)
                // switch off error message
                dogsLoadError.postValue(false)
                // switch off waiting spinner
                loading.postValue(false)
            }
    }

    // fetches data with Retrofit library from remote API
    private fun fetchFromRemoteWithRetrofit() {
        //TODO !!!DON'T FORGET TO ADD INTERNET PERMISSION BEFORE IMPLEMENTING!!!
        // set loading flag to true
        loading.value = true
        disposable.add(
            // set it to a different thread(passing this call to the background thread)
            dogsService.getDogs()
                .subscribeOn(Schedulers.newThread())
                // retrieve it from a background to the main thread for displaying
                .observeOn(AndroidSchedulers.mainThread())
                // pass our Single object here, it's created and implemented
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>() {
                    // get list of DogBreed objects
                    override fun onSuccess(dogsList: List<DogBreed>) {
                        // set received list to observable mutable list
                        dogs.value = dogsList
                        // switch off error message
                        dogsLoadError.value = false
                        // switch off waiting spinner
                        loading.value = false
                    }
                    // get an error
                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        // print stack of error to handling it
                        e.printStackTrace()
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}