package com.aliumujib.preferences.domain.usecase

import com.aliumujib.preferences.domain.AppPreferences
import javax.inject.Inject

class GetAppThemeUseCase @Inject constructor(
    private val appPreferences: AppPreferences
) {
    operator fun invoke() = appPreferences.getTheme()
}
