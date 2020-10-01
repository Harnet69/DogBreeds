package com.harnet.dogbreeds.util

import android.graphics.Bitmap
import java.util.concurrent.ExecutionException

class OwnImageManager {
    private val imageDownloader = OwnImageLoader()

    fun getImageByLink(imageLink: String?): Bitmap? {
        return try {
            val img = imageDownloader.doInBackground(imageLink)
//            imageDownloader.cancel(true) // close a thread
            img
        } catch (e: ExecutionException) {
            e.printStackTrace()
            null
        } catch (e: InterruptedException) {
            e.printStackTrace()
            null
        }
    }
}