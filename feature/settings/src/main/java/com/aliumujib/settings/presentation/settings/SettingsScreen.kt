package com.aliumujib.settings.presentation.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aliumujib.common.util.getAppVersionName
import com.aliumujib.catbrowser.settings.R
import com.aliumujib.settings.domain.model.Setting
import com.aliumujib.settings.presentation.settings.components.FeedbackDialog
import com.aliumujib.settings.presentation.settings.components.SettingCard
import com.aliumujib.settings.presentation.settings.components.ThemesDialog
import com.ramcosta.composedestinations.annotation.Destination
import io.eyram.iconsax.IconSax

@Destination
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val settingsUiState by viewModel.states.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is SettingsUiEvents.ShowErrorMessage -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }


    SettingsScreenContent(
        state = settingsUiState,
        onEvent = { event ->
            when (event) {
                SettingsUiActions.ChangeThemeClicked -> {
                    viewModel.trackUserEvent("Themes Dialog Opened")
                    viewModel.setShowThemesDialogState()
                }

                SettingsUiActions.ReportOrSuggestClicked -> {
                    viewModel.trackUserEvent("Feedback Dialog Opened")
                    viewModel.setShowFeedbackDialogState()
                }

                is SettingsUiActions.SendFeedbackClicked -> {
                    keyboardController?.hide()
                    viewModel.setFeedbackState(
                        value = event.feedback,
                        error = if (event.feedback.isEmpty()) {
                            "Feedback cannot be empty"
                        } else {
                            null
                        }
                    )

                    if (event.feedback.isEmpty()) {
                        return@SettingsScreenContent
                    }

                    sendFeedbackIntent(event, viewModel, context)
                }

                is SettingsUiActions.OnFeedbackChanged -> {
                    viewModel.setFeedbackState(event.feedback)
                }

                SettingsUiActions.OnDismissThemesDialog -> {
                    viewModel.trackUserEvent("Themes Dialog Closed")
                    viewModel.setShowThemesDialogState()
                }

                is SettingsUiActions.OnSelectTheme -> {
                    viewModel.trackUserEvent("Theme Selected: ${event.themeValue}")
                    viewModel.updateTheme(event.themeValue)
                }

                SettingsUiActions.OnDismissFeedbackDialog -> {
                    viewModel.setShowFeedbackDialogState()
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    )

}

@Composable
private fun SettingsScreenContent(
    state: SettingsUiState,
    onEvent: (SettingsUiActions) -> Unit,
    snackbarHost: @Composable () -> Unit,
) {
    Scaffold(
        snackbarHost = { snackbarHost() },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = R.string.settings_tab_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    ) { paddingValues ->
        val context = LocalContext.current

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(context.settingsOptions()) { setting ->
                    SettingCard(
                        title = setting.title,
                        icon = setting.icon,
                        onClick = { settingsOption ->
                            when (settingsOption) {
                                context.getString(R.string.change_your_theme) -> {
                                    onEvent(SettingsUiActions.ChangeThemeClicked)
                                }

                                context.getString(R.string.suggest_or_report_anything) -> {
                                    onEvent(SettingsUiActions.ReportOrSuggestClicked)
                                }
                            }
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = stringResource(R.string.app_version, getAppVersionName(context)),
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 11.sp
                )

                Text(
                    text = stringResource(R.string.made_with_by_aliu_mujib),
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 12.sp
                )
            }
        }

        if (state.shouldShowThemesDialog) {
            ThemesDialog(
                onDismiss = {
                    onEvent(SettingsUiActions.OnDismissThemesDialog)
                },
                onSelectTheme = {
                    onEvent(SettingsUiActions.OnSelectTheme(it))
                }
            )
        }

        if (state.shouldShowFeedbackDialog) {
            FeedbackDialog(
                currentFeedbackString = state.feedbackState.text,
                isError = state.feedbackState.error != null,
                error = state.feedbackState.error,
                onDismiss = {
                    onEvent(SettingsUiActions.OnDismissFeedbackDialog)
                },
                onFeedbackChange = { newValue ->
                    onEvent(SettingsUiActions.OnFeedbackChanged(newValue))
                },
                onClickSend = { feedback ->
                    onEvent(SettingsUiActions.SendFeedbackClicked(feedback))
                }
            )
        }
    }
}

private fun Context.settingsOptions() = listOf(
    Setting(
        title = getString(R.string.change_your_theme),
        icon = IconSax.Linear.Moon
    ),
    Setting(
        title = getString(R.string.suggest_or_report_anything),
        icon = IconSax.Linear.Text
    ),
)

private fun sendFeedbackIntent(
    event: SettingsUiActions.SendFeedbackClicked,
    viewModel: SettingsViewModel,
    context: Context
) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("aliuabdulmujib@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "APP FEEDBACK")
            putExtra(Intent.EXTRA_TEXT, event.feedback)
            viewModel.trackUserEvent("Feedback Sent: $event.feedback")
        }
        context.startActivity(intent)

        viewModel.setShowFeedbackDialogState()
        viewModel.setFeedbackState("")
    } catch (e: Exception) {
        Toast.makeText(
            context,
            "No Email Application Found",
            Toast.LENGTH_SHORT
        )
            .show()
    }
}
