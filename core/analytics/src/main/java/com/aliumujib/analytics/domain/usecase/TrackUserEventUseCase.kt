package com.aliumujib.analytics.domain.usecase

import com.aliumujib.analytics.domain.repository.AnalyticsRepository
import javax.inject.Inject

class TrackUserEventUseCase @Inject constructor(
    private val analyticsRepository: AnalyticsRepository
) {
    operator fun invoke(name: String) = analyticsRepository.trackUserEvent(name)
}
