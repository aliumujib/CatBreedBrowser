package com.aliumujib.songs.domain.usecases

import app.cash.turbine.test
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

class StreamBreedsListUseCaseTest {

    @MockK
    private lateinit var catBreedsRepository: CatBreedsRepository

    private val dispatcherProvider: DispatcherProvider = TestDispatcherProviderImpl()

    private lateinit var streamBreedsListUseCase: StreamBreedsListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        streamBreedsListUseCase = StreamBreedsListUseCase(catBreedsRepository, dispatcherProvider)
    }

    @Test
    fun `when streamBreedsListUseCase is invoked and repository contains breed data, then it should emit correct breed list`() = runTest {
        val breedList = SharedDummyData.breedList
        coEvery { catBreedsRepository.streamBreedsList() } returns flowOf(breedList)

        streamBreedsListUseCase(Unit).test {
            assertThat(awaitItem()).isEqualTo(breedList)
            awaitComplete()
        }
    }

    @Test
    fun `when streamBreedsListUseCase is invoked and repository fails to fetch breed data, then it should emit an error`() = runTest {
        val exception = IllegalStateException("Failed to fetch breed data")
        coEvery { catBreedsRepository.streamBreedsList() } returns flow { throw exception }

        streamBreedsListUseCase(Unit).test {
            val error = awaitError()
            assertThat(error).isInstanceOf(IllegalStateException::class.java)
        }
    }

}