package com.aliumujib.database.di

import android.content.Context
import androidx.room.Room
import com.aliumujib.database.Constants
import com.aliumujib.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            Constants.APP_DATABASE
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesBreedsDao(database: AppDatabase) = database.breedsDao

    @Provides
    @Singleton
    fun providesFavoritesDAO(database: AppDatabase) = database.favoritesDAO
}
