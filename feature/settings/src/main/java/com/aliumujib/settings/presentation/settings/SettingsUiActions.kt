package com.aliumujib.settings.presentation.settings

sealed interface SettingsUiEvents {
    class ShowErrorMessage(val message: String) : SettingsUiEvents
}

sealed interface SettingsUiActions {
    class SendFeedbackClicked(val feedback: String) :
        SettingsUiActions

    data object ChangeThemeClicked : SettingsUiActions

    data object ReportOrSuggestClicked : SettingsUiActions

    data object OnDismissThemesDialog :
        SettingsUiActions

    data object OnDismissFeedbackDialog :
        SettingsUiActions

    data class OnFeedbackChanged(val feedback: String) : SettingsUiActions

    data class OnSelectTheme(val themeValue: Int) : SettingsUiActions

}