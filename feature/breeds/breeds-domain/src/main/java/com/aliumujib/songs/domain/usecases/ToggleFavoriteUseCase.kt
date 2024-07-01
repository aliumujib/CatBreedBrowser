package com.aliumujib.songs.domain.usecases

import com.aliumujib.common.domain.usecases.NoParamsException
import com.aliumujib.common.domain.usecases.SuspendUseCase
import com.aliumujib.common.domain.utils.DispatcherProvider
import com.aliumujib.model.Breed
import com.aliumujib.model.BreedId
import com.aliumujib.songs.domain.repo.CatBreedsRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val catBreedsRepository: CatBreedsRepository,
    dispatcherProvider: DispatcherProvider,
) : SuspendUseCase<BreedId, BreedId>(dispatcherProvider) {

    override suspend fun execute(params: BreedId?) : BreedId {
        params ?: throw NoParamsException()
        if (catBreedsRepository.isFavorite(params)) {
            catBreedsRepository.removeFavorite(params)
        } else {
            catBreedsRepository.addFavorite(params)
        }
        return params
    }

}
