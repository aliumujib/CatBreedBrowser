package com.aliumujib.common.domain.usecases

import com.aliumujib.common.domain.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in Params, T> constructor(
    private val dispatcherProvider: DispatcherProvider,
) {

    /**
     * Function which builds Flow instance based on given arguments
     * @param params initial use case arguments
     */
    abstract fun build(params: Params? = null): Flow<T>

    operator fun invoke(params: Params? = null): Flow<T> {
        return this.build(params)
            .flowOn(dispatcherProvider.io)

    }
}
