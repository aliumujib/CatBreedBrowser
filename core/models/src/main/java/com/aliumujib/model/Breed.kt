package com.aliumujib.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Breed(
    val id: BreedId,
    val name: String,
    val weight: Weight,
    val urls: Urls,
    val attributes: Attributes,
    val characteristics: Characteristics,
    val wikipediaUrl: String,
    val hypoallergenic: Int,
    val referenceImageUrl: String,
    val isFavorite: Boolean
) : Parcelable

@[JvmInline Parcelize]
value class BreedId(val data: String) : Parcelable

@Parcelize
data class Weight(
    val imperial: String,
    val metric: String
) : Parcelable

@Parcelize
data class Urls(
    val cfaUrl: String,
    val vetstreetUrl: String,
    val vcahospitalsUrl: String
) : Parcelable

@Parcelize
data class Attributes(
    val temperament: String,
    val origin: String,
    val countryCodes: String,
    val countryCode: String,
    val description: String,
    val lifeSpan: String,
    val indoor: Int,
    val lap: Int,
    val altNames: String
) : Parcelable

@Parcelize
data class Characteristics(
    val adaptability: Int,
    val affectionLevel: Int,
    val childFriendly: Int,
    val dogFriendly: Int,
    val energyLevel: Int,
    val grooming: Int,
    val healthIssues: Int,
    val intelligence: Int,
    val sheddingLevel: Int,
    val socialNeeds: Int,
    val strangerFriendly: Int,
    val vocalisation: Int,
    val experimental: Int,
    val hairless: Int,
    val natural: Int,
    val rare: Int,
    val rex: Int,
    val suppressedTail: Int,
    val shortLegs: Int
) : Parcelable
