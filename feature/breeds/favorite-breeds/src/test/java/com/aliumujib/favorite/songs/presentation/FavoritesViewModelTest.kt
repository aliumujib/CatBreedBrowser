package com.aliumujib.favorite.songs.presentation

import app.cash.turbine.test
import com.aliumujib.common.test.MainCoroutineRule
import com.aliumujib.common.test.SharedDummyData
import com.aliumujib.favorite.breeds.presentation.FavoritesUiEvents
import com.aliumujib.favorite.breeds.presentation.FavoritesUiState
import com.aliumujib.favorite.breeds.presentation.FavoritesViewModel
import com.aliumujib.songs.domain.usecases.StreamFavoritesListUseCase
import com.aliumujib.songs.domain.usecases.ToggleFavoriteUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoritesViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @MockK
    private lateinit var streamFavoritesListUseCase: StreamFavoritesListUseCase

    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { streamFavoritesListUseCase() } returns flowOf(SharedDummyData.breedList)
        viewModel = FavoritesViewModel(streamFavoritesListUseCase, toggleFavoriteUseCase)
    }

    @Test
    fun `given breeds, when ViewModel is initialized, then update state with breeds`() = runTest {
        every { streamFavoritesListUseCase() } returns flowOf(SharedDummyData.breedList)

        viewModel.states.test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(FavoritesUiState.Success::class.java)
            val successState = item as FavoritesUiState.Success
            assertThat(successState.breeds).isEqualTo(SharedDummyData.breedList)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when toggleFavorite is called, then update breed favorite status`() = runTest {
        val breed = SharedDummyData.breed1
        coEvery { toggleFavoriteUseCase(breed.id) } returns Result.failure(IllegalStateException())

        viewModel.events.test {
            viewModel.toggleFavorite(breed)
            val event = awaitItem()
            assertThat(event).isInstanceOf(FavoritesUiEvents.ShowError::class.java)
            cancelAndConsumeRemainingEvents()
        }
    }

}
