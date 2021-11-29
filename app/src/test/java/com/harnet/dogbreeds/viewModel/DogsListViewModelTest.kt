package com.harnet.dogbreeds.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.atilsamancioglu.artbookhilttesting.MainCoroutineRule
import com.atilsamancioglu.artbookhilttesting.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import com.harnet.dogbreeds.roomDb.DogBreed
import com.harnet.dogbreeds.repository.DogRepositoryFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DogsListViewModelTest {

    // Set only one, main thread
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: DogsListViewModel

    @Before
    fun setup() {
        // Test Doubles using a fake repository
        viewModel = DogsListViewModel(DogRepositoryFake())
    }

    @Test
    fun `fetch from database get a dogs list`() {
        val dogExample = DogBreed(
            "1", "Test breed", "100 years",
            "Test group", "Test", "Test", "test.com"
        )
        viewModel.storeDogInDatabase(listOf(dogExample))
        viewModel.refreshFromDatabase()
        val dogsList = viewModel.mDogs.getOrAwaitValueTest()

        assertThat(dogsList).contains(dogExample)
    }
}