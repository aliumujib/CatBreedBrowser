package com.aliumujib.settings.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.eyram.iconsax.IconSax

@Composable
fun ThemesDialog(onDismiss: () -> Unit, onSelectTheme: (Int) -> Unit) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Themes",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                ThemeItem(
                    themeName = "Use System Settings",
                    themeValue = com.aliumujib.designsystem.theme.Theme.FOLLOW_SYSTEM.themeValue,
                    icon = IconSax.Linear.Settings,
                    onSelectTheme = onSelectTheme
                )
                ThemeItem(
                    themeName = "Light Mode",
                    themeValue = com.aliumujib.designsystem.theme.Theme.LIGHT_THEME.themeValue,
                    icon = IconSax.Linear.Sun,
                    onSelectTheme = onSelectTheme
                )
                ThemeItem(
                    themeName = "Dark Mode",
                    themeValue = com.aliumujib.designsystem.theme.Theme.DARK_THEME.themeValue,
                    icon = IconSax.Linear.Moon,
                    onSelectTheme = onSelectTheme
                )
                ThemeItem(
                    themeName = "Material You",
                    themeValue = com.aliumujib.designsystem.theme.Theme.MATERIAL_YOU.themeValue,
                    icon = IconSax.Linear.PictureFrame,
                    onSelectTheme = onSelectTheme
                )
            }
        },
        confirmButton = {
            Text(
                text = "OK",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() }
            )
        }
    )
}
