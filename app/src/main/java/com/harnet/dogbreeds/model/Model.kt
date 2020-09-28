package com.harnet.dogbreeds.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// ROOM's annotation
@Entity
data class DogBreed(
    @ColumnInfo(name = "breed_id") // ROOM's annotation
    @SerializedName("id")// fields names from API Json's file are matching with DogBreed fields(Retrofit's annotation)
    val breedId: String?,

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")
    val dogBreed: String?,

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    val lifespan: String?,

    @ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    val breedGrupp: String?,

    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    val bredFor: String?,

    @SerializedName("temperament")
    val temperament: String?,

    @ColumnInfo(name = "dog_url")
    @SerializedName("url")
    val imageURL: String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}