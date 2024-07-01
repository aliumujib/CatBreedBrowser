package com.aliumujib.songs.data.mapper

import com.aliumujib.database.model.BreedDBModel
import com.aliumujib.model.Attributes
import com.aliumujib.model.Breed
import com.aliumujib.model.BreedId
import com.aliumujib.model.Characteristics
import com.aliumujib.model.Urls
import com.aliumujib.model.Weight
import com.aliumujib.network.model.BreedAPIModel
import javax.inject.Inject

class BreedMapper @Inject constructor() {

    fun mapToUIModel(dbModel: BreedDBModel, isFavorite: Boolean): Breed {
        return with(dbModel) {
            Breed(
                id = BreedId(id),
                name = name,
                weight = Weight(imperial = weightImperial, metric = weightMetric),
                urls = Urls(cfaUrl = cfaUrl, vetstreetUrl = vetstreetUrl, vcahospitalsUrl = vcahospitalsUrl),
                attributes = Attributes(
                    temperament = temperament,
                    origin = origin,
                    countryCodes = countryCodes,
                    countryCode = countryCode,
                    description = description,
                    lifeSpan = lifeSpan,
                    indoor = indoor,
                    lap = lap,
                    altNames = altNames
                ),
                characteristics = Characteristics(
                    adaptability = adaptability,
                    affectionLevel = affectionLevel,
                    childFriendly = childFriendly,
                    dogFriendly = dogFriendly,
                    energyLevel = energyLevel,
                    grooming = grooming,
                    healthIssues = healthIssues,
                    intelligence = intelligence,
                    sheddingLevel = sheddingLevel,
                    socialNeeds = socialNeeds,
                    strangerFriendly = strangerFriendly,
                    vocalisation = vocalisation,
                    experimental = experimental,
                    hairless = hairless,
                    natural = natural,
                    rare = rare,
                    rex = rex,
                    suppressedTail = suppressedTail,
                    shortLegs = shortLegs
                ),
                wikipediaUrl = wikipediaUrl,
                hypoallergenic = hypoallergenic,
                referenceImageUrl = referenceImageUrl,
                isFavorite = isFavorite
            )
        }
    }

    fun mapToDBModel(breedAPIModel: BreedAPIModel): BreedDBModel {
        return with(breedAPIModel) {
            BreedDBModel(
                id = id,
                name = name,
                weightImperial = weight.imperial,
                weightMetric = weight.metric,
                cfaUrl = cfaUrl.orEmpty(),
                vetstreetUrl = vetstreetUrl.orEmpty(),
                vcahospitalsUrl = vcahospitalsUrl.orEmpty(),
                temperament = temperament,
                origin = origin,
                countryCodes = countryCodes,
                countryCode = countryCode,
                description = description,
                lifeSpan = lifeSpan,
                indoor = indoor,
                lap = lap,
                altNames = altNames.orEmpty(),
                adaptability = adaptability,
                affectionLevel = affectionLevel,
                childFriendly = childFriendly,
                dogFriendly = dogFriendly,
                energyLevel = energyLevel,
                grooming = grooming,
                healthIssues = healthIssues,
                intelligence = intelligence,
                sheddingLevel = sheddingLevel,
                socialNeeds = socialNeeds,
                strangerFriendly = strangerFriendly,
                vocalisation = vocalisation,
                experimental = experimental,
                hairless = hairless,
                natural = natural,
                rare = rare,
                rex = rex,
                suppressedTail = suppressedTail,
                shortLegs = shortLegs,
                wikipediaUrl = wikipediaUrl.orEmpty(),
                hypoallergenic = hypoallergenic,
                referenceImageUrl = referenceImage?.imageUrl.orEmpty()
            )
        }
    }
}