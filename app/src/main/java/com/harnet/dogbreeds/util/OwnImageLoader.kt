package com.harnet.dogbreeds.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import com.harnet.dogbreeds.BuildConfig
import kotlinx.coroutines.CoroutineScope
import java.net.HttpURLConnection
import java.net.URL

class OwnImageLoader : BaseParser() {
    private var myBitmap: Bitmap? = null

    // the method return an image asynchronicity
    fun doInBackground(vararg urls: String?): Bitmap? {
        myBitmap = manageImageInputStream(urls as Array<String>) // can be a cause of a crash
        return myBitmap
    }

    // connect to URL
    private fun createConnectionToURL(urls: Array<String>): HttpURLConnection? {
        val url: URL
        val connection: HttpURLConnection
        return try {
            url = URL(urls[0])
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            connection
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // handle an Input Stream
    private fun manageImageInputStream(urls: Array<String>): Bitmap? {
        val connection = createConnectionToURL(urls)
        return try {
            if (BuildConfig.DEBUG && connection == null) {
                error("Assertion failed")
            }
            connection!!.connect()
            val iN = connection.inputStream
            myBitmap = BitmapFactory.decodeStream(iN)
            myBitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}