package com.harnet.dogbreeds.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job()

    // when running job finishes -  Main thread have been returned
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    // when BaseViewModel clears
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}