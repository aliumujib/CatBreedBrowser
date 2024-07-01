package com.aliumujib.catbrowser.di

import com.aliumujib.catbrowser.BuildConfig
import com.aliumujib.network.auth.BuildConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BuildConfigModule {

    @Singleton
    @Provides
    fun providesBuildConfiguration(): BuildConfiguration {
        return BuildConfiguration(
            debug = BuildConfig.DEBUG,
            appId = BuildConfig.APPLICATION_ID,
            buildType = BuildConfig.BUILD_TYPE,
            versionCode = BuildConfig.VERSION_CODE,
            versionName = BuildConfig.VERSION_NAME,
            baseUrl = "https://api.thecatapi.com/v1/",
            apiKey = "live_WRRSUbYuEPByUfgMPRWgq3lPRWWYBLNUwmtzHr5L7FAkCPynWDM23oldD5kNQFfm"
        )
    }

}
