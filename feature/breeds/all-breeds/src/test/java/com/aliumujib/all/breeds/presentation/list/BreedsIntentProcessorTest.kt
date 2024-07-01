package com.aliumujib.all.breeds.presentation.list

import org.junit.Assert.*

import app.cash.turbine.test
import com.aliumujib.common.test.SharedDummyData
import com.aliumujib.songs.domain.usecases.StreamBreedsListUseCase
import com.aliumujib.songs.domain.usecases.ToggleFavoriteUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class BreedsIntentProcessorTest {

    @MockK
    private lateinit var streamBreedsListUseCase: StreamBreedsListUseCase

    @MockK
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @InjectMockKs
    private lateinit var processor: BreedsIntentProcessor

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when FetchCatBreeds intent is received, then emit Loading and Success`() = runTest {
        val breeds = SharedDummyData.breedList
        every { streamBreedsListUseCase() } returns flowOf(breeds)

        val intent = BreedsContract.BreedsUiIntent.FetchCatBreeds

        processor.intentToResult(intent).test {
            assertThat(awaitItem()).isEqualTo(BreedsContract.BreedsResult.FetchCatBreedsResult.Loading)
            assertThat(awaitItem()).isEqualTo(BreedsContract.BreedsResult.FetchCatBreedsResult.Success(breeds))
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when FetchCatBreeds intent throws error, then emit Loading and Error`() = runTest {
        val error = RuntimeException("Error fetching breeds")
        every { streamBreedsListUseCase() } returns flow { throw error }

        val intent = BreedsContract.BreedsUiIntent.FetchCatBreeds

        processor.intentToResult(intent).test {
            assertThat(awaitItem()).isEqualTo(BreedsContract.BreedsResult.FetchCatBreedsResult.Loading)
            assertThat(awaitItem()).isEqualTo(BreedsContract.BreedsResult.FetchCatBreedsResult.Error(error))
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when ToggleFavouriteStatus intent is received, then emit Success`() = runTest {
        val breedId = SharedDummyData.breed2.id
        coEvery { toggleFavoriteUseCase(breedId) } returns Result.success(breedId)

        val intent = BreedsContract.BreedsUiIntent.ToggleFavouriteStatus(breedId)

        processor.intentToResult(intent).test {
            assertThat(awaitItem()).isEqualTo(BreedsContract.BreedsResult.ToggleFavouriteStatusResult.Success(breedId))
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when ToggleFavouriteStatus intent fails, then emit Error`() = runTest {
        val breedId = SharedDummyData.breed3.id
        val error = RuntimeException("Error toggling favourite status")
        coEvery { toggleFavoriteUseCase(breedId) } returns Result.failure(error)

        val intent = BreedsContract.BreedsUiIntent.ToggleFavouriteStatus(breedId)

        processor.intentToResult(intent).test {
            assertThat(awaitItem()).isEqualTo(BreedsContract.BreedsResult.ToggleFavouriteStatusResult.Error(error))
            cancelAndConsumeRemainingEvents()
        }
    }
}
