package com.harnet.dogbreeds.roomDb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.harnet.dogbreeds.model.DogBreed
import com.harnet.dogbreeds.model.DogDAO
import com.harnet.dogbreeds.model.DogsDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*
    Add to build.gradle to android section
    packagingOptions {
        exclude "win32-x86/attach_hotspot_windows.dll"
        exclude "win32-x86-64/attach_hotspot_windows.dll"
        exclude "META-INF/licenses/ASM"
        exclude "META-INF/AL2.0"
        exclude "META-INF/LGPL2.1"
    }
 */
@SmallTest
@ExperimentalCoroutinesApi
class DogDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var database: DogsDatabase

    private lateinit var dao: DogDAO

    private val dogExample = DogBreed(
        "1", "Test breed", "100 years",
        "Test group", "Test", "Test", "test.com"
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DogsDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = database.dogDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addDogsToDbTesting() = runBlocking{
        dao.insertAll(dogExample)
        val dogsList = dao.getAllDogs()

        assertThat(dogsList).contains(dogExample)
    }

    @Test
    fun deleteDogsFromDbTesting() = runBlocking{
        dao.insertAll(dogExample)
        val dogsList = dao.getAllDogs()
        dao.deleteAllDogs()

        assertThat(dogsList).isNotEmpty()
    }

    @Test
    fun getDogByIdFromDbTesting() = runBlocking{
        dao.insertAll(dogExample)

        val dog = dogExample.breedId?.let { dao.getDog(it) }

        assertThat(dog).isNotNull()
    }

    @Test
    fun getDogByUuIdFromDbTesting() = runBlocking{
        dao.insertAll(dogExample)

        val dog =  dao.getDog(1)

        assertThat(dog).isNotNull()
    }

}