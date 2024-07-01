package com.aliumujib.songs.domain.usecases

import com.aliumujib.common.domain.usecases.FlowUseCase
import com.aliumujib.common.domain.utils.DispatcherProvider
import com.aliumujib.model.Breed
import com.aliumujib.songs.domain.repo.CatBreedsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StreamBreedsListUseCase @Inject constructor(
    private val catBreedsRepository: CatBreedsRepository,
    dispatcherProvider: DispatcherProvider,
) : FlowUseCase<Unit, List<Breed>>(dispatcherProvider) {

    override fun build(params: Unit?): Flow<List<Breed>> {
        return catBreedsRepository.streamBreedsList()
    }

}
