package com.aliumujib.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliumujib.database.Constants.BREEDS_TABLE

@Entity(tableName = BREEDS_TABLE)
data class BreedDBModel(
    @PrimaryKey
    val id: String,
    val name: String,
    val weightImperial: String,
    val weightMetric: String,
    val cfaUrl: String,
    val vetstreetUrl: String,
    val vcahospitalsUrl: String,
    val temperament: String,
    val origin: String,
    val countryCodes: String,
    val countryCode: String,
    val description: String,
    val lifeSpan: String,
    val indoor: Int,
    val lap: Int,
    val altNames: String,
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
    val shortLegs: Int,
    val wikipediaUrl: String,
    val hypoallergenic: Int,
    val referenceImageUrl: String
)