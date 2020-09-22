package com.harnet.dogbreeds.util

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class OwnDataParser {
    fun parseDataFromJSONStr(jsonStr: String?) {
        try {
            val jsonArray = JSONArray(jsonStr);
            for (i in 0 until jsonArray.length()) {
                val obj: JSONObject = jsonArray.get(i) as JSONObject
                    val id =obj.getString("id")
                    val name = obj.getString("name")
                    val lifeSpan = obj.getString("life_span")
//                    val breedGroup = obj.getString("breed_group")
//                    val breedFor = obj.getString("bred_for")
//                    val temperament = obj.getString("temperament")
                    val url = obj.getString("url")

                println("$id $name $lifeSpan $url")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}