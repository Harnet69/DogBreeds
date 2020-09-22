package com.harnet.dogbreeds.util

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DataParser {
    fun parseDataFromJSONStr(jsonStr: String?) {
        try {
            val jsonObject = JSONObject(jsonStr)
            println(jsonObject)
            val weather = jsonObject.getString("weather")
            Log.i("current_weather", "weather: $weather")
            println(weather)
            val weatherArr = JSONArray(weather)
            for (param in 0 until weatherArr.length()) {
                val partOfWeather = weatherArr.getJSONObject(param)
                Log.i("current_weather", "main: " + partOfWeather.getString("main"))
                Log.i("current_weather", "description: " + partOfWeather.getString("description"))
                Log.i("current_weather", "icon: " + partOfWeather.getString("icon"))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    companion object {
        fun parseDataFromJSONFile(jsonStr: String?) {
            try {
                val jsonObject = JSONObject(jsonStr)
                println(jsonObject)
                val weather = jsonObject.getString("weather")
                Log.i("current_weather", "weather: $weather")
                //            System.out.println(weather);
                val weatherArr = JSONArray(weather)
                //                Log.i("current_weather", "main: " + weatherArr.getJSONObject(0).getString("main"));
                for (param in 0 until weatherArr.length()) {
                    val partOfWeather = weatherArr.getJSONObject(param)
                    Log.i("current_weather", "main: " + partOfWeather.getString("main"))
                    Log.i(
                        "current_weather",
                        "description: " + partOfWeather.getString("description")
                    )
                    Log.i("current_weather", "icon: " + partOfWeather.getString("icon"))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
}