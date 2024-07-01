package com.aliumujib.network.auth

data class BuildConfiguration(
    val debug: Boolean,
    val appId: String,
    val buildType: String,
    val versionCode: Int,
    val versionName: String,
    val apiKey: String,
    val baseUrl: String
)