package com.harnet.dogbreeds.util

import com.harnet.dogbreeds.model.DogBreed
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.ExecutionException

class OwnDataParser {

    fun parseDataFromJSONStr(): MutableList<DogBreed> {
    val dogs = mutableListOf<DogBreed>()

        var content: String? = null
        try {
            content = OwnDataDownloader().execute("https://raw.githubusercontent.com/DevTides/DogsApi/master/dogs.json").get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }


        val jsonArray = JSONArray(content);
        for (i in 0 until jsonArray.length()) {
            val obj: JSONObject = jsonArray.get(i) as JSONObject
            val id: String = obj.optString("id")
            val name: String = obj.optString("name")
            val lifeSpan: String = obj.optString("life_span")
            val breedGroup: String = obj.optString("breed_group")
            val bredFor: String = obj.optString("bred_for")
            val temperament: String = obj.optString("temperament")
            val url: String = obj.optString("url")

            dogs.add(DogBreed(id, name, lifeSpan, breedGroup, bredFor, temperament, url))
//            println("$id $name $lifeSpan $breedGroup $bredFor $temperament $url")
        }
        return dogs
    }
}