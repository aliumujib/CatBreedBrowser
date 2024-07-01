package com.aliumujib.designsystem.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aliumujib.designsystem.animation.ThreeDotsLoading
import com.aliumujib.designsystem.preview.AppPreview
import com.aliumujib.designsystem.theme.AppTheme
import com.aliumujib.designsystem.theme.squircleMedium

@Composable
fun AppContainedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
    ),
) {
    AppContainedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        contentPadding = contentPadding,
        colors = colors,
    ) {
        ButtonText(text)
    }
}

@Composable
fun AppSecondaryContainedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    enabled: Boolean = true,
    isLoading: Boolean = false,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
    ),
) {
    AppContainedButton(
        text = text,
        onClick = onClick,
        enabled = enabled,
        isLoading = isLoading,
        modifier = modifier,
        contentPadding = contentPadding,
        colors = colors,
    )
}

@Composable
fun AppContainedButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
    ),
) {
    AppContainedButton(
        onClick = {
            if (enabled && !isLoading) {
                onClick()
            }
        },
        enabled = enabled || isLoading,
        modifier = modifier,
        contentPadding = contentPadding,
        colors = colors,
    ) {
        LoadingButton(isLoading, text)
    }
}

@Composable
private fun LoadingButton(isLoading: Boolean, text: String) {
    val loadingTransition = updateTransition(isLoading, label = "loading transition")
    loadingTransition.AnimatedContent(
        transitionSpec = {
            fadeIn(tween(durationMillis = 220, delayMillis = 90)) togetherWith fadeOut(tween(90))
        },
        contentAlignment = Alignment.Center,
    ) { loading ->
        if (loading) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                // render the text too so that the same space is taken in all cases
                ButtonText(text, Modifier.alpha(0f))
                ThreeDotsLoading()
            }
        } else {
            ButtonText(text)
        }
    }
}

@Composable
fun AppContainedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
    ),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.squircleMedium,
        contentPadding = contentPadding,
        colors = colors,
    ) {
        content()
    }
}

@Composable
private fun ButtonText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}

@AppPreview
@Composable
private fun PreviewHedvigContainedButton() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AppContainedButton("Hello there", {}, Modifier.padding(24.dp))
        }
    }
}

@AppPreview
@Composable
private fun PreviewHedvigSecondaryContainedButton() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AppSecondaryContainedButton("Hello there", {}, Modifier.padding(24.dp))
        }
    }
}

@AppPreview
@Composable
private fun PreviewLoadingHedvigContainedButton() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AppContainedButton(
                text = "Hello there",
                onClick = {},
                isLoading = true,
                modifier = Modifier.padding(24.dp),
            )
        }
    }
}