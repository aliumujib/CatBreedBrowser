package com.aliumujib.network.auth

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        if (!apiKey.isNullOrEmpty()) {
            requestBuilder.addHeader("x-api-key", apiKey)
        }

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }

}
