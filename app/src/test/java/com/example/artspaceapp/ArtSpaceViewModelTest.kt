package com.example.artspaceapp

import org.junit.Assert.assertEquals
import org.junit.Test

class ArtSpaceViewModelTest {
    @Test fun navigation_preservesBoundsAndVisitsEveryArtwork() {
        val model = ArtSpaceViewModel()
        assertEquals(1, model.uiState.value.currentStep)
        model.previousArtwork()
        assertEquals(1, model.uiState.value.currentStep)
        model.nextArtwork()
        assertEquals(2, model.uiState.value.currentStep)
        model.nextArtwork()
        model.nextArtwork()
        assertEquals(3, model.uiState.value.currentStep)
        model.previousArtwork()
        assertEquals(2, model.uiState.value.currentStep)
        model.previousArtwork()
        assertEquals(1, model.uiState.value.currentStep)
    }
}
