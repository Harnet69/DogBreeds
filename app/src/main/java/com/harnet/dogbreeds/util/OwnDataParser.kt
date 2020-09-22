package com.harnet.dogbreeds.util

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class OwnDataParser {
    fun parseDataFromJSONStr(jsonStr: String?) {
        var trimmedStr: String? = null
        if (jsonStr != null) {
            // there was a problem with the API!!!
            trimmedStr = jsonStr.substring(1, jsonStr.length-1)
//            println(trimmedStr)
        }
        try {
            //TODO get only the first object
            val jsonObject = JSONObject(trimmedStr!!)
//            println(jsonObject)

            //work solution
            val jsonArray = JSONArray(jsonStr);
            for (i in 0 until jsonArray.length()) {
                val obj: JSONObject = jsonArray.get(i) as JSONObject;
                println(obj.getString("name"))


//                JSONArray results = patient.getJSONArray("results");
//                String indexForPhone =  patientProfile.getJSONObject(0).getString("indexForPhone"));
            }

            //TODO think about parsing multiple array
//            val dogsArray = JSONArray(jsonObject)
//            Log.i("JsonObjectrr", "parseDataFromJSONStr: array " + dogsArray)
//            for(param in 0 until dogsArray.length()){
//                val dog = dogsArray.getJSONObject(param)
//                Log.i("JsonObjectrr", "parseDataFromJSONStr: param $dog")
//            }

//            val weather = jsonObject.getString("weather")
//
//            val weatherArr = JSONArray(weather)
//            for (param in 0 until weatherArr.length()) {
//                val partOfWeather = weatherArr.getJSONObject(param)
//                Log.i("current_weather", "main: " + partOfWeather.getString("main"))
//                Log.i("current_weather", "description: " + partOfWeather.getString("description"))
//                Log.i("current_weather", "icon: " + partOfWeather.getString("icon"))
//            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}