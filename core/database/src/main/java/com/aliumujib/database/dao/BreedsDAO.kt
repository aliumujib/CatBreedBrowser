package com.aliumujib.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aliumujib.database.model.BreedDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBreeds(vararg breeds: BreedDBModel)

    @Query("SELECT * FROM breeds")
    suspend fun getAllBreeds(): List<BreedDBModel>

    @Query("SELECT * FROM breeds")
    fun streamBreedsList(): Flow<List<BreedDBModel>>

    @Query("SELECT * FROM breeds WHERE id = :id")
    fun streamBreedDetails(id: String): Flow<BreedDBModel>

    @Query("DELETE FROM breeds WHERE id = :id")
    suspend fun removeBreed(id: String)
}

