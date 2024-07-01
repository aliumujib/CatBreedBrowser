package com.aliumujib.songs.domain.usecases

import app.cash.turbine.test
import com.aliumujib.common.domain.utils.DispatcherProvider
import com.aliumujib.common.test.SharedDummyData
import com.aliumujib.common.test.TestDispatcherProviderImpl
import com.aliumujib.songs.domain.repo.CatBreedsRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import kotlinx.coroutines.flow.flow

class StreamFavoritesListUseCaseTest {

    @MockK
    private lateinit var catBreedsRepository: CatBreedsRepository

    private val dispatcherProvider: DispatcherProvider = TestDispatcherProviderImpl()

    lateinit var streamFavoritesListUseCase: StreamFavoritesListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        streamFavoritesListUseCase = StreamFavoritesListUseCase(catBreedsRepository, dispatcherProvider)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given repository returns favorite breeds when StreamFavoritesListUseCase is invoked, then it should emit the favorite breeds list`() = runTest {
        coEvery { catBreedsRepository.streamFavoritesList() } returns flowOf(SharedDummyData.breedList)

        streamFavoritesListUseCase(Unit).test {
            assertThat(awaitItem()).isEqualTo(SharedDummyData.breedList)
            awaitComplete()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given repository fails to fetch data when StreamFavoritesListUseCase is invoked, then it should emit an error`() = runTest {
        val exception = Exception("Error fetching favorite breeds")
        coEvery { catBreedsRepository.streamFavoritesList() } returns flow { throw exception }

        streamFavoritesListUseCase(Unit).test {
            val error = awaitError()
            assertThat(error).isInstanceOf(Exception::class.java)
            assertThat(error.message).isEqualTo("Error fetching favorite breeds")
        }
    }
}
