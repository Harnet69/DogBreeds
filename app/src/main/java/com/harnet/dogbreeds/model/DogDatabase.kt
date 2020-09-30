package com.harnet.dogbreeds.model

import androidx.room.Database
import androidx.room.RoomDatabase

// singleton for handling with a database
@Database(entities = arrayOf(DogBreed::class), version = 1)
abstract class DogDatabase: RoomDatabase() {
    // return DogDAO interface
    abstract fun dogDAO(): DogDAO

    // create static methods and variables which can accessed from outside the class
    companion object{
        //`volatile`, meaning that writes to this field are immediately made visible to other threads
        @Volatile private var instance: DogDatabase? = null

    }
}