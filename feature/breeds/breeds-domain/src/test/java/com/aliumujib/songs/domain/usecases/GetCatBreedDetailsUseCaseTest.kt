package com.aliumujib.songs.domain.usecases

import app.cash.turbine.test
import com.aliumujib.common.domain.usecases.NoParamsException
import com.aliumujib.common.domain.utils.DispatcherProvider
import com.aliumujib.common.test.SharedDummyData
import com.aliumujib.common.test.TestDispatcherProviderImpl
import com.aliumujib.songs.domain.repo.CatBreedsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCatBreedDetailsUseCaseTest {

    @MockK
    private lateinit var catBreedsRepository: CatBreedsRepository

    private val dispatcherProvider: DispatcherProvider = TestDispatcherProviderImpl()

    private lateinit var getCatBreedDetailsUseCase: GetCatBreedDetailsUseCase


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getCatBreedDetailsUseCase = GetCatBreedDetailsUseCase(catBreedsRepository, dispatcherProvider)
    }

    @Test
    fun `given valid BreedId when GetCatBreedDetailsUseCase is invoked, then it should emit the corresponding Breed`() = runTest {
        val breedId = SharedDummyData.breed1.id
        val expectedBreed = SharedDummyData.breed1

        coEvery { catBreedsRepository.getBreedDetails(breedId) } returns flowOf(expectedBreed)

        getCatBreedDetailsUseCase(breedId).test {
            assertThat(awaitItem()).isEqualTo(expectedBreed)
            awaitComplete()
        }
    }

    @Test
    fun `given repository failure when StreamBreedsListUseCase is invoked, then it should emit an error`() = runTest {
        val exception = Exception("Failed to stream breed data due to network issue")
        val breedId = SharedDummyData.breed1.id

        coEvery { catBreedsRepository.getBreedDetails(breedId) } returns flow { throw exception }

        getCatBreedDetailsUseCase(breedId).test {
            val error = awaitError()
            assertThat(error).isInstanceOf(Exception::class.java)
        }
    }

    @Test
    fun `given null BreedId when GetCatBreedDetailsUseCase is invoked, then it should throw NoParamsException`() = runTest {
        try {
            getCatBreedDetailsUseCase(null).test { }
            assert(false) { "Expected NoParamsException to be thrown" }
        } catch (e: NoParamsException) {
            assert(true)
        }
    }

}
