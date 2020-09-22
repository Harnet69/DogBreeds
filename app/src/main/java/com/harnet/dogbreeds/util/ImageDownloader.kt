package com.harnet.dogbreeds.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import com.harnet.dogbreeds.BuildConfig
import java.net.HttpURLConnection
import java.net.URL

class ImageDownloader : AsyncTask<String?, Void?, Bitmap?>() {
    private var myBitmap: Bitmap? = null

    // the method return an image asynchronicity
    override fun doInBackground(vararg urls: String?): Bitmap? {
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
            val `in` = connection.inputStream
            myBitmap = BitmapFactory.decodeStream(`in`)
            myBitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}