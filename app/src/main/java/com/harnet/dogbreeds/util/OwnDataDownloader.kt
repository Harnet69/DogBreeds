package com.harnet.dogbreeds.util

import android.os.AsyncTask
import android.util.Log
import com.harnet.dogbreeds.model.DogBreed
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class OwnDataDownloader : AsyncTask<String?, Void?, String?>() {
    override fun doInBackground(vararg urls: String?): String? {
        val site = StringBuilder()
        try {
            val url = URL(urls[0])
            val connection = url.openConnection() as HttpURLConnection
            val `in` = connection.inputStream
            val reader = InputStreamReader(`in`)
            var data = reader.read()
            while (data != -1) {
                val current = data.toChar()
                site.append(current)
                data = reader.read()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return site.toString()
    }

}