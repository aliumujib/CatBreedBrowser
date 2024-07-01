package com.aliumujib.breed.details.presentation

import com.aliumujib.breed.common.presentation.IntentProcessor
import com.aliumujib.songs.domain.usecases.GetCatBreedDetailsUseCase
import com.aliumujib.songs.domain.usecases.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class BreedDetailsIntentProcessor @Inject constructor(
    private val streamCatBreedDetailsUseCase: GetCatBreedDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : IntentProcessor<BreedDetailsContract.BreedDetailsUiIntent, BreedDetailsContract.BreedDetailsResult> {

    override fun intentToResult(viewIntent: BreedDetailsContract.BreedDetailsUiIntent): Flow<BreedDetailsContract.BreedDetailsResult> {
        return flow {
            when (viewIntent) {
               is BreedDetailsContract.BreedDetailsUiIntent.FetchCatBreedDetails -> {
                    emit(BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Loading)
                    try {
                        emitAll(
                            streamCatBreedDetailsUseCase(viewIntent.breedId).map {
                                BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Success(it)
                            }
                        )
                    } catch (e: Exception) {
                        emit(BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Error(e))
                    }
                }

                is BreedDetailsContract.BreedDetailsUiIntent.ToggleFavouriteStatus -> {
                    toggleFavoriteUseCase(viewIntent.breedId).onSuccess {
                        emit(BreedDetailsContract.BreedDetailsResult.ToggleFavouriteStatusResult.Success(it))
                    }.onFailure {
                        emit(BreedDetailsContract.BreedDetailsResult.ToggleFavouriteStatusResult.Error(it))
                    }
                }
            }
        }
    }

}