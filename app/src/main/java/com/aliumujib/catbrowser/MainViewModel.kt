package com.aliumujib.catbrowser

import androidx.core.splashscreen.SplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliumujib.preferences.domain.usecase.GetAppThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAppThemeUseCase: GetAppThemeUseCase,
) : ViewModel(), SplashScreen.KeepOnScreenCondition {

    private val hasRequiredPermissions = MutableStateFlow(false)

    private var isLoadingData: Boolean = true

    val state = hasRequiredPermissions
        .map { hasSeenPermissions ->
            MainUiState(hasSeenPermissions)
        }.onEach {
            viewModelScope.launch {
                delay(500L)
                isLoadingData = false
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainUiState(),
        )

    val theme = getAppThemeUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0,
        )

    fun setGrantedPermissions(granted: Boolean) {
        hasRequiredPermissions.value = granted
    }

    override fun shouldKeepOnScreen(): Boolean {
        return isLoadingData
    }

}

data class MainUiState(
    val hasGrantedPermissions: Boolean = false,
)
