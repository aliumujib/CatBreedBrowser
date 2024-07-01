
package com.aliumujib.preferences.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.aliumujib.preferences.data.AppPreferencesImpl
import com.aliumujib.preferences.domain.AppPreferences
import com.aliumujib.preferences.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    fun provideDatastorePreferences(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(Constants.APP_PREFERENCES)
            }
        )

    @Provides
    @Singleton
    fun provideMealtimeSettings(dataStore: DataStore<Preferences>): AppPreferences =
        AppPreferencesImpl(dataStore)
}
