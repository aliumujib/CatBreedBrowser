package com.aliumujib.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "favorites")
data class FavoritesDBModel(
    @PrimaryKey val breedId: String
)

data class FavoritesWithBreeds(
    @Embedded val favorite: FavoritesDBModel,
    @Relation(
        parentColumn = "breedId",
        entityColumn = "id",
        entity = BreedDBModel::class
    )
    val breed: BreedDBModel?
)