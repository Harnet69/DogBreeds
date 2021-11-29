package com.harnet.dogbreeds.roomDb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

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
@HiltAndroidTest
class DogDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var database: DogsDatabase
//    lateinit var database: DogsDatabase

    private lateinit var dao: DogDAO

    private val dogExample = DogBreed(
        "1", "Test breed", "100 years",
        "Test group", "Test", "Test", "test.com"
    )

    @Before
    fun setup() {
        /*
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DogsDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

         */

        hiltRule.inject()

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
    fun getDogByIdFromDbTesting() = runBlocking{
        dao.insertAll(dogExample)

        val dog = dogExample.breedId?.let { dao.getDogById(it) }

        assertThat(dog).isNotNull()
    }

    @Test
    fun deleteDogsFromDbTesting() = runBlocking{
        dao.insertAll(dogExample)
        val dogsList = dao.getAllDogs()
        dao.deleteAllDogs()

        assertThat(dogsList).isNotEmpty()
    }

}