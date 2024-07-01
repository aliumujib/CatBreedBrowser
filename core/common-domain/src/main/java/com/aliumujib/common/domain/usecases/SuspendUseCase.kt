package com.aliumujib.common.domain.usecases

import com.aliumujib.common.domain.utils.DispatcherProvider
import kotlinx.coroutines.withContext

abstract class SuspendUseCase<in P, R : Any?>(
    private val dispatcherProvider: DispatcherProvider,
) {

    suspend operator fun invoke(params: P? = null): Result<R> {
        return withContext(dispatcherProvider.io) {
            try {
                Result.success(execute(params))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    abstract suspend fun execute(params: P?): R
}
