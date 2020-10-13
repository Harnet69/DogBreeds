package com.harnet.dogbreeds.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseParser: CoroutineScope {
    private val job = Job()

    // when running job finishes -  Main thread have been returned
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}