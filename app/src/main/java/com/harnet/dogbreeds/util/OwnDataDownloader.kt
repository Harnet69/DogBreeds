package com.harnet.dogbreeds.util

import android.os.AsyncTask
import android.util.Log
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class OwnDataDownloader : AsyncTask<String?, Void?, String?>() {
    private val dataParser = OwnDataParser()

    override fun doInBackground(vararg urls: String?): String? {
        val result = StringBuilder()
        val url: URL
        val connection: HttpURLConnection
        return try {
            url = URL(urls[0])
            connection = url.openConnection() as HttpURLConnection
            val `in` = connection.inputStream
            val reader = InputStreamReader(`in`)
            var data = reader.read()
            while (data != -1) {
                val current = data.toChar()
                result.append(current)
                data = reader.read()
            }
            result.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // callback after getting a dataForecast
    override fun onPostExecute(s: String?) {
        super.onPostExecute(s)
        //get dataForecast from API
        dataParser.parseDataFromJSONStr(s)
    }
}