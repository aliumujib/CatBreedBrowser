package com.aliumujib.designsystem.components

import android.view.KeyEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliumujib.designsystem.R
import io.eyram.iconsax.IconSax

@Composable
fun BoxyTextField(
    modifier: Modifier = Modifier,
    label: String,
    inputString: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    error: String?,
    loading: Boolean,
    onInputChanged: (String) -> Unit,
    onClear: () -> Unit,
) {
    OutlinedTextField(
        value = inputString,
        onValueChange = onInputChanged,
        modifier = modifier,
        label = { Text(label) },
        trailingIcon = {
            TrailingIcon(error, loading, inputString, onClear)
        },
        isError = error != null,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
    )

    AnimatedVisibility(visible = error != null) {
        Text(
            text = error.orEmpty(),
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error),
            modifier = Modifier.padding(horizontal = 16.dp),
        )
    }
}

@Composable
private fun TrailingIcon(
    error: String?,
    loading: Boolean,
    inputString: String,
    onClear: () -> Unit
) {
    if (error != null) {
        Icon(
            painter = painterResource(id = IconSax.Outline.InfoCircle),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
        )
    } else if (loading) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
    } else if (inputString.isNotBlank()) {
        IconButton(onClick = onClear) {
            Icon(
                painter = painterResource(id = IconSax.Outline.CloseCircle),
                contentDescription = stringResource(
                    R.string.icon_description_clear_all,
                    inputString
                ),
            )
        }
    }
}

fun Modifier.submitOnEnter(action: () -> Unit) = composed {
    val keyboardController = LocalSoftwareKeyboardController.current
    onKeyEvent { keyEvent ->
        if (keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
            keyboardController?.hide()
            action()
            true
        } else {
            false
        }
    }
}
