package com.aliumujib.all.breeds.presentation.list

import androidx.lifecycle.ViewModel
import com.aliumujib.breed.common.presentation.MVI
import com.aliumujib.breed.common.presentation.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val intentProcessor: BreedsIntentProcessor,
    private val stateReducer: BreedsStateReducer,
) : ViewModel(),
    MVI<BreedsContract.BreedsUiState, BreedsContract.BreedsUiIntent, BreedsContract.BreedsResult, BreedsContract.BreedsSideEffect> by mvi(
        BreedsContract.BreedsUiState.Initial,
        intentProcessor,
        stateReducer
    ) {

    fun start() {
        processActions()
        onAction(BreedsContract.BreedsUiIntent.FetchCatBreeds)
    }

}
