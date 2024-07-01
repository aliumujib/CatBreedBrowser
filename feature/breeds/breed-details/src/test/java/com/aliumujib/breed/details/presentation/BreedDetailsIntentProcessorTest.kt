package com.aliumujib.breed.details.presentation

import app.cash.turbine.test
import com.aliumujib.common.test.SharedDummyData
import com.aliumujib.songs.domain.usecases.GetCatBreedDetailsUseCase
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

class BreedDetailsIntentProcessorTest {

    @MockK
    private lateinit var streamCatBreedDetailsUseCase: GetCatBreedDetailsUseCase

    @MockK
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @InjectMockKs
    private lateinit var processor: BreedDetailsIntentProcessor

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when FetchCatBreedDetails intent is received, then emit Loading and Success`() = runTest {
        val breed = SharedDummyData.breed1
        val breedId = breed.id

        every { streamCatBreedDetailsUseCase(breedId) } returns flowOf(breed)

        val intent = BreedDetailsContract.BreedDetailsUiIntent.FetchCatBreedDetails(breedId)

        processor.intentToResult(intent).test {
            assertThat(awaitItem()).isEqualTo(BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Loading)
            assertThat(awaitItem()).isEqualTo(BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Success(breed))
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when FetchCatBreedDetails intent throws error, then emit Loading and Error`() = runTest {
        val breedId = SharedDummyData.breed1.id

        val error = RuntimeException("Error fetching breed details")
        every { streamCatBreedDetailsUseCase(breedId) } returns flow { throw error }

        val intent = BreedDetailsContract.BreedDetailsUiIntent.FetchCatBreedDetails(breedId)

        processor.intentToResult(intent).test {
            assertThat(awaitItem()).isEqualTo(BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Loading)
            assertThat(awaitItem()).isEqualTo(BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Error(error))
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when ToggleFavouriteStatus intent is received, then emit Success`() = runTest {
        val breedId = SharedDummyData.breed1.id
        coEvery { toggleFavoriteUseCase(breedId) } returns Result.success(breedId)

        val intent = BreedDetailsContract.BreedDetailsUiIntent.ToggleFavouriteStatus(breedId)

        processor.intentToResult(intent).test {
            assertThat(awaitItem()).isEqualTo(BreedDetailsContract.BreedDetailsResult.ToggleFavouriteStatusResult.Success(breedId))
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when ToggleFavouriteStatus intent fails, then emit Error`() = runTest {
        val breed = SharedDummyData.breed1
        val breedId = breed.id

        val error = RuntimeException("Error toggling favourite status")
        coEvery { toggleFavoriteUseCase(breedId) } returns Result.failure(error)

        val intent = BreedDetailsContract.BreedDetailsUiIntent.ToggleFavouriteStatus(breedId)

        processor.intentToResult(intent).test {
            assertThat(awaitItem()).isEqualTo(BreedDetailsContract.BreedDetailsResult.ToggleFavouriteStatusResult.Error(error))
            cancelAndConsumeRemainingEvents()
        }
    }
}
