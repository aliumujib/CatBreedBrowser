package com.aliumujib.songs.domain.usecases

import com.aliumujib.common.domain.usecases.FlowUseCase
import com.aliumujib.common.domain.usecases.NoParamsException
import com.aliumujib.common.domain.utils.DispatcherProvider
import com.aliumujib.model.Breed
import com.aliumujib.model.BreedId
import com.aliumujib.songs.domain.repo.CatBreedsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatBreedDetailsUseCase @Inject constructor(
    private val catBreedsRepository: CatBreedsRepository,
    dispatcherProvider: DispatcherProvider,
) : FlowUseCase<BreedId, Breed>(dispatcherProvider) {

    override fun build(params: BreedId?): Flow<Breed> {
        params ?: throw NoParamsException()
        return catBreedsRepository.getBreedDetails(params)
    }

}
