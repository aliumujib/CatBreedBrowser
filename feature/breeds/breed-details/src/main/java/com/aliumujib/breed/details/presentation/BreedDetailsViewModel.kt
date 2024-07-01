package com.aliumujib.breed.details.presentation

import androidx.lifecycle.ViewModel
import com.aliumujib.model.BreedId
import com.aliumujib.breed.common.presentation.MVI
import com.aliumujib.breed.common.presentation.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel @Inject constructor(
    private val intentProcessor: BreedDetailsIntentProcessor,
    private val stateReducer: BreedDetailsStateReducer,
) : ViewModel(),
    MVI<BreedDetailsContract.BreedDetailsUiState, BreedDetailsContract.BreedDetailsUiIntent,
            BreedDetailsContract.BreedDetailsResult, BreedDetailsContract.BreedDetailsSideEffect> by mvi(
        BreedDetailsContract.BreedDetailsUiState.Initial,
        intentProcessor,
        stateReducer
    ) {

    fun start(breedId: BreedId) {
        processActions()
        onAction(BreedDetailsContract.BreedDetailsUiIntent.FetchCatBreedDetails(breedId))
    }

}
