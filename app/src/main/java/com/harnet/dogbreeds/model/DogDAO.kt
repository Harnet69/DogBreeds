package com.harnet.dogbreeds.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDAO {
    @Insert
    suspend fun insertAll(vararg dogs: DogBreed): List<Long> // dogs is an expanded list of individual elements!!!

    @Query("SELECT * FROM dogbreed")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("DELETE FROM dogbreed")
    suspend fun deleteAllDogs()

    @Query("SELECT * FROM dogbreed WHERE uuid = :dogUuid")
    suspend fun getDog(dogUuid: Int): DogBreed

    @Query("SELECT * FROM dogbreed WHERE breed_id = :id")
    suspend fun getDog(id: String): DogBreed
}