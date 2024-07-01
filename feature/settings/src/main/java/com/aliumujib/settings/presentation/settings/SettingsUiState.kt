package com.aliumujib.settings.presentation.settings

import androidx.compose.runtime.Immutable
import com.aliumujib.common.state.TextFieldState

@Immutable
data class SettingsUiState(
    val shouldShowThemesDialog: Boolean = false,
    val shouldShowFeedbackDialog: Boolean = false,
    val feedbackState: TextFieldState = TextFieldState(),
)
