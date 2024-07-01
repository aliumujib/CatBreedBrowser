package com.aliumujib.preferences.domain

import kotlinx.coroutines.flow.Flow

interface AppPreferences {
    suspend fun saveTheme(themeValue: Int)
    fun getTheme(): Flow<Int>
    suspend fun saveLastLoggedIn(lastLoggedIn: Long)
    fun getLastLoggedIn(): Flow<Long>
    suspend fun clear()
}
