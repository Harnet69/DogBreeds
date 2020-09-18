package com.harnet.dogbreeds.model

import com.google.gson.annotations.SerializedName

data class DogBreed(
    // fields names from API Json's file are matching with DogBreed fields
    @SerializedName("id")
    val breedId: String?,
    @SerializedName("name")
    val dogBreed: String?,
    @SerializedName("life_span")
    val lifespan: String?,
    @SerializedName("breed_group")
    val breedGrupp: String?,
    @SerializedName("bred_for")
    val bredFor: String?,
    @SerializedName("temperament")
    val temperament: String?,
    @SerializedName("url")
    val imageURL: String?
)