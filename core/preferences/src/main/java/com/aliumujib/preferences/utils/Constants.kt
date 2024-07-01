package com.aliumujib.preferences.utils

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

object Constants {
    val THEME_OPTIONS = intPreferencesKey(name = "theme_option")
    val LAST_LOGIN_DATE_KEY = longPreferencesKey("last_login_date")

    const val APP_PREFERENCES = "APP_PREFERENCES"
}
