package com.aliumujib.preferences.data

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.aliumujib.preferences.domain.AppPreferences
import com.aliumujib.preferences.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import javax.inject.Inject

class AppPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : AppPreferences {
    override suspend fun saveTheme(themeValue: Int) {
        dataStore.edit { preferences ->
            preferences[Constants.THEME_OPTIONS] = themeValue
        }
    }

    override fun getTheme(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[Constants.THEME_OPTIONS] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
    }

    override suspend fun saveLastLoggedIn(lastLoggedIn: Long) {
        dataStore.edit { preferences ->
            preferences[Constants.LAST_LOGIN_DATE_KEY] = lastLoggedIn
        }
    }

    override fun getLastLoggedIn(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[Constants.LAST_LOGIN_DATE_KEY] ?: Instant.DISTANT_PAST.epochSeconds
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
