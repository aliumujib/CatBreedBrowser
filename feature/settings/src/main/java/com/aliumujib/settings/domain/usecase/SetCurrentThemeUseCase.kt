package com.aliumujib.settings.domain.usecase

import com.aliumujib.preferences.domain.AppPreferences
import javax.inject.Inject

class SetCurrentThemeUseCase @Inject constructor(
    private val appPreferences: AppPreferences
) {
    suspend operator fun invoke(theme: Int) {
        appPreferences.saveTheme(theme)
    }
}
