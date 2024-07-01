package com.aliumujib.songs.data.di

import com.aliumujib.database.dao.BreedsDAO
import com.aliumujib.database.dao.FavoritesDAO
import com.aliumujib.network.CatAPIService
import com.aliumujib.songs.data.mapper.BreedMapper
import com.aliumujib.songs.data.repo.CatBreedsRepositoryImpl
import com.aliumujib.songs.domain.repo.CatBreedsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BreedsDataModule {

    @Provides
    @Singleton
    fun provideSongsRepository(
        songDao: BreedsDAO,
        favoritesDao: FavoritesDAO,
        catAPIService: CatAPIService,
        mapper: BreedMapper
    ): CatBreedsRepository {

        return CatBreedsRepositoryImpl(
            songDao,
            favoritesDao,
            catAPIService,
            mapper
        )
    }
}
