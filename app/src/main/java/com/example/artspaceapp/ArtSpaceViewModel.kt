package com.example.artspaceapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ArtSpaceViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ArtSpaceUiState())
    val uiState: StateFlow<ArtSpaceUiState> = _uiState.asStateFlow()

    fun previousArtwork() {
        _uiState.update { it.copy(currentStep = (it.currentStep - 1).coerceAtLeast(1)) }
    }

    fun nextArtwork() {
        _uiState.update { it.copy(currentStep = (it.currentStep + 1).coerceAtMost(3)) }
    }
}
