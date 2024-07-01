package com.aliumujib.analytics.di

import android.content.Context
import com.aliumujib.analytics.BuildConfig
import com.aliumujib.analytics.data.repository.AnalyticsRepositoryImpl
import com.aliumujib.analytics.domain.repository.AnalyticsRepository
import com.mixpanel.android.mpmetrics.MixpanelAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

    @Singleton
    @Provides
    fun providesMixPaneApi(@ApplicationContext context: Context): MixpanelAPI {
        return MixpanelAPI.getInstance(context, BuildConfig.MIXPANEL_TOKEN, false)
    }

    @Singleton
    @Provides
    fun providesAnalyticsRepository(mixpanelAPI: MixpanelAPI): AnalyticsRepository {
        return AnalyticsRepositoryImpl(mixpanelAPI)
    }
}
