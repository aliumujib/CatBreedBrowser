package com.aliumujib.songs.data.repo

import app.cash.turbine.test
import com.aliumujib.database.dao.BreedsDAO
import com.aliumujib.database.dao.FavoritesDAO
import com.aliumujib.database.model.FavoritesDBModel
import com.aliumujib.database.model.FavoritesWithBreeds
import com.aliumujib.model.BreedId
import com.aliumujib.network.CatAPIService
import com.aliumujib.songs.data.TestDummyData
import com.aliumujib.songs.data.mapper.BreedMapper
import com.aliumujib.songs.domain.repo.CatBreedsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CatBreedsRepositoryImplTest {

    @MockK(relaxed = true)
    private lateinit var breedsDao: BreedsDAO

    @MockK
    private lateinit var favoritesDao: FavoritesDAO

    @MockK
    private lateinit var catsAPIService: CatAPIService

    private val mapper: BreedMapper = BreedMapper()

    private lateinit var repository: CatBreedsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CatBreedsRepositoryImpl(breedsDao, favoritesDao, catsAPIService, mapper)
    }

    @Test
    fun `given breeds are present in the database when streamBreedsList is called, then breeds are streamed`() = runTest {
            val dbBreeds = TestDummyData.breedDBModelList
            val uiBreeds = TestDummyData.breedList

            every { breedsDao.streamBreedsList() } returns flowOf(dbBreeds)
            coEvery { catsAPIService.getBreeds() } returns TestDummyData.breedAPIModelList
            coEvery { breedsDao.saveBreeds(any()) } returns Unit
            coEvery { favoritesDao.isFavorite(any()) } returns true

            repository.streamBreedsList().test {
                val emitted = awaitItem()
                assertThat(emitted).isEqualTo(uiBreeds)
                awaitComplete()
            }

            coVerify(exactly = 1) { catsAPIService.getBreeds() }
            coVerify { breedsDao.saveBreeds(*anyVararg()) }
        }

    @Test
    fun `given breed ID when addFavorite is called, then the favorite is added`() = runTest {
        val breedId = BreedId("abys")

        coEvery { favoritesDao.addFavorite(any()) } returns Unit

        repository.addFavorite(breedId)

        coVerify { favoritesDao.addFavorite(FavoritesDBModel(breedId.data)) }
    }

    @Test
    fun `given breed ID when removeFavorite is called, then the favorite is removed`() = runTest {
        val breedId = BreedId("aege")

        coEvery { favoritesDao.removeFavorite(any()) } returns Unit

        repository.removeFavorite(breedId)

        coVerify { favoritesDao.removeFavorite(FavoritesDBModel(breedId.data)) }
    }

    @Test
    fun `given breed ID when isFavorite is called, then return true if the breed is favorite`() =
        runTest {
            val breedId = BreedId("abob")

            coEvery { favoritesDao.isFavorite(breedId.data) } returns true

            val result = repository.isFavorite(breedId)

            assertThat(result).isTrue()
        }

    @Test
    fun `given favorite breeds when streamFavoritesList is called, then return list of favorite breeds`() =
        runTest {
            val favoriteBreeds = listOf(TestDummyData.breed1, TestDummyData.breed3)
            val dbFavorites = listOf(
                FavoritesWithBreeds(FavoritesDBModel("abys"), TestDummyData.breedDBModel1),
                FavoritesWithBreeds(FavoritesDBModel("abob"), TestDummyData.breedDBModel3)
            )

            every { favoritesDao.streamAllFavoritesWithBreeds() } returns flowOf(dbFavorites)
            repository.streamFavoritesList().test {
                val emitted = awaitItem()
                assertThat(emitted).isEqualTo(favoriteBreeds)
                awaitComplete()
            }
        }
}