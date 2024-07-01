package com.aliumujib.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aliumujib.database.model.FavoritesDBModel
import com.aliumujib.database.model.FavoritesWithBreeds
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoritesDBModel)

    @Delete
    suspend fun removeFavorite(favorite: FavoritesDBModel)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE breedId = :id)")
    suspend fun isFavorite(id: String): Boolean

    @Transaction
    @Query("SELECT * FROM favorites")
    fun streamAllFavoritesWithBreeds(): Flow<List<FavoritesWithBreeds>>

}
