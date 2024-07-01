package com.aliumujib.analytics.data.repository

import com.aliumujib.analytics.BuildConfig
import com.aliumujib.analytics.domain.repository.AnalyticsRepository
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject

class AnalyticsRepositoryImpl(
    private val mixpanelAPI: MixpanelAPI
) : AnalyticsRepository {

    override fun trackUserEvent(eventName: String, eventProperties: JSONObject?) {
        if (BuildConfig.BUILD_TYPE != "release") return
        eventProperties
            ?.let { mixpanelAPI.track(eventName, eventProperties) }
            ?: mixpanelAPI.track(eventName)
    }

    override fun setUserProfile(userID: String, userProperties: JSONObject?) {
        userProperties
            ?.let {
                mixpanelAPI.identify(userID)
                mixpanelAPI.people.set(it)
            } ?: mixpanelAPI.identify(userID)
    }
}
