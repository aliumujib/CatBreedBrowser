package com.aliumujib.songs.data.repo

import com.aliumujib.database.dao.FavoritesDAO
import com.aliumujib.database.dao.BreedsDAO
import com.aliumujib.database.model.FavoritesDBModel
import com.aliumujib.model.Breed
import com.aliumujib.model.BreedId
import com.aliumujib.network.CatAPIService
import com.aliumujib.network.model.BreedImageAPIModel
import com.aliumujib.songs.data.mapper.BreedMapper
import com.aliumujib.songs.domain.repo.CatBreedsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatBreedsRepositoryImpl @Inject constructor(
    private val breedsDao: BreedsDAO,
    private val favoritesDao: FavoritesDAO,
    private val catsAPIService: CatAPIService,
    private val mapper: BreedMapper
) : CatBreedsRepository {

    override fun streamBreedsList(): Flow<List<Breed>> {
        return breedsDao.streamBreedsList()
            .onStart {
                catsAPIService.getBreeds().also { apiModels ->
                    breedsDao.saveBreeds(*apiModels.map(mapper::mapToDBModel).toTypedArray())
                }
            }
            .map { breeds ->
                breeds.map {
                    mapper.mapToUIModel(it, isFavorite(BreedId(it.id)))
                }
            }
    }

    override suspend fun addFavorite(id: BreedId) {
        favoritesDao.addFavorite(FavoritesDBModel(id.data))
    }

    override suspend fun removeFavorite(id: BreedId) {
        favoritesDao.removeFavorite(FavoritesDBModel(id.data))
    }

    override suspend fun isFavorite(id: BreedId): Boolean = favoritesDao.isFavorite(id.data)

    override fun streamFavoritesList(): Flow<List<Breed>> {
        return favoritesDao.streamAllFavoritesWithBreeds()
            .map { favorites ->
                favorites.map { mapper.mapToUIModel(it.breed!!, true) }
            }
    }

    override fun getBreedDetails(id: BreedId): Flow<Breed> {
        return breedsDao.streamBreedDetails(id.data)
            .onStart {
                catsAPIService.getBreedById(id.data, 1).also { item ->
                    val data =
                        item.copy(referenceImage = BreedImageAPIModel("https://cdn2.thecatapi.com/images/${item.referenceImageId}.jpg"))
                    breedsDao.saveBreeds(mapper.mapToDBModel(data))
                }
            }
            .map { item ->
                mapper.mapToUIModel(item, isFavorite(BreedId(item.id)))
            }
    }

}