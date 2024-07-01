package com.aliumujib.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliumujib.database.dao.FavoritesDAO
import com.aliumujib.database.dao.BreedsDAO
import com.aliumujib.database.model.BreedDBModel
import com.aliumujib.database.model.FavoritesDBModel

@Database(
    entities = [
        BreedDBModel::class,
        FavoritesDBModel::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract val breedsDao: BreedsDAO

    abstract val favoritesDAO: FavoritesDAO

}
