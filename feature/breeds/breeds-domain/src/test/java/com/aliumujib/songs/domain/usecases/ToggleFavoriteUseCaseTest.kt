package com.aliumujib.songs.domain.usecases

import com.aliumujib.common.domain.usecases.NoParamsException
import com.aliumujib.common.domain.utils.DispatcherProvider
import com.aliumujib.common.test.TestDispatcherProviderImpl
import com.aliumujib.model.BreedId
import com.aliumujib.songs.domain.repo.CatBreedsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ToggleFavoriteUseCaseTest {

    @MockK
    private lateinit var catBreedsRepository: CatBreedsRepository

    private val dispatcherProvider: DispatcherProvider = TestDispatcherProviderImpl()

    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        toggleFavoriteUseCase = ToggleFavoriteUseCase(catBreedsRepository, dispatcherProvider)
    }

    @Test
    fun `given params is null when execute is called, then NoParamsException should be thrown`() = runTest {
        val result  = toggleFavoriteUseCase(null)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(NoParamsException::class.java)
    }

    @Test
    fun `given params in state not favorite when ToggleFavoriteUseCase is invoked, then addFavorite should be called`() = runTest {
        val params = BreedId("breedId")
        coEvery { catBreedsRepository.isFavorite(params) } returns false
        coEvery { catBreedsRepository.addFavorite(params) } returns Unit

        toggleFavoriteUseCase(params) // Use the invoke function

        coVerify { catBreedsRepository.addFavorite(params) }
    }

    @Test
    fun `given params in state favorite when ToggleFavoriteUseCase is invoked, then removeFavorite should be called`() = runTest {
        val params = BreedId("breedId")
        coEvery { catBreedsRepository.isFavorite(params) } returns true
        coEvery { catBreedsRepository.removeFavorite(params) } returns Unit

        toggleFavoriteUseCase(params) // Use the invoke function

        coVerify { catBreedsRepository.removeFavorite(params) }
    }

}