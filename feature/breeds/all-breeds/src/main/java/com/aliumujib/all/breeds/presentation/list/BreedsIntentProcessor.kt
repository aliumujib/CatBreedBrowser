package com.aliumujib.all.breeds.presentation.list

import com.aliumujib.breed.common.presentation.IntentProcessor
import com.aliumujib.songs.domain.usecases.StreamBreedsListUseCase
import com.aliumujib.songs.domain.usecases.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BreedsIntentProcessor @Inject constructor(
    private val streamBreedsListUseCase: StreamBreedsListUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : IntentProcessor<BreedsContract.BreedsUiIntent, BreedsContract.BreedsResult> {


    override fun intentToResult(viewIntent: BreedsContract.BreedsUiIntent): Flow<BreedsContract.BreedsResult> {
        return flow {
            when (viewIntent) {
                BreedsContract.BreedsUiIntent.FetchCatBreeds -> {
                    emit(BreedsContract.BreedsResult.FetchCatBreedsResult.Loading)
                    try {
                        emitAll(
                            streamBreedsListUseCase().map {
                                BreedsContract.BreedsResult.FetchCatBreedsResult.Success(it)
                            }
                        )
                    } catch (e: Exception) {
                        emit(BreedsContract.BreedsResult.FetchCatBreedsResult.Error(e))
                    }
                }

                is BreedsContract.BreedsUiIntent.ToggleFavouriteStatus -> {
                    toggleFavoriteUseCase(viewIntent.breedId).onSuccess {
                        emit(BreedsContract.BreedsResult.ToggleFavouriteStatusResult.Success(it))
                    }.onFailure {
                        emit(BreedsContract.BreedsResult.ToggleFavouriteStatusResult.Error(it))
                    }
                }
            }
        }
    }

}
