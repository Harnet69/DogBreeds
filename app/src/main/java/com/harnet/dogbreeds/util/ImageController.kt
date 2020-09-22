package com.harnet.dogbreeds.util

import android.graphics.Bitmap
import java.util.concurrent.ExecutionException

class ImageController {
    private val imageDownloader = ImageDownloader()
    fun getImageByLink(imageLink: String?): Bitmap? {
        return try {
            val img = imageDownloader.execute(imageLink).get()!!
            imageDownloader.cancel(true) // close a thread
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