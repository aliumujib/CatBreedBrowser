package com.aliumujib.network.di

import com.aliumujib.network.CatAPIService
import com.aliumujib.network.auth.AuthInterceptor
import com.aliumujib.network.auth.BuildConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        buildConfiguration: BuildConfiguration
    ): AuthInterceptor {
        return AuthInterceptor(buildConfiguration.apiKey)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .apply {
                addInterceptor(authInterceptor)
                addInterceptor(httpLoggingInterceptor)
                callTimeout(15, TimeUnit.SECONDS)
                connectTimeout(15, TimeUnit.SECONDS)
                writeTimeout(15, TimeUnit.SECONDS)
                readTimeout(15, TimeUnit.SECONDS)
            }

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, buildConfiguration: BuildConfiguration): Retrofit {
        return Retrofit.Builder()
            .baseUrl(buildConfiguration.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMealDbApi(retrofit: Retrofit): CatAPIService {
        return retrofit.create()
    }
}
