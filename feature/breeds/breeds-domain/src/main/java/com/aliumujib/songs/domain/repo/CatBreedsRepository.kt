package com.aliumujib.songs.domain.repo

import com.aliumujib.model.Breed
import com.aliumujib.model.BreedId
import kotlinx.coroutines.flow.Flow

interface CatBreedsRepository {
    fun streamBreedsList(): Flow<List<Breed>>
    suspend fun addFavorite(id: BreedId)
    suspend fun removeFavorite(id: BreedId)
    suspend fun isFavorite(id: BreedId): Boolean
    fun streamFavoritesList(): Flow<List<Breed>>
    fun getBreedDetails(id: BreedId) :  Flow<Breed>
}