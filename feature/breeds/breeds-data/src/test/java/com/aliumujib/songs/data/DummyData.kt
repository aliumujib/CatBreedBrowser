package com.aliumujib.songs.data

import com.aliumujib.database.model.BreedDBModel
import com.aliumujib.model.Attributes
import com.aliumujib.model.Breed
import com.aliumujib.model.BreedId
import com.aliumujib.model.Characteristics
import com.aliumujib.model.Urls
import com.aliumujib.model.Weight
import com.aliumujib.network.model.BreedAPIModel
import com.aliumujib.network.model.BreedImageAPIModel
import com.aliumujib.network.model.WeightAPIModel

object TestDummyData {

    val breedAPIModel1 = BreedAPIModel(
        id = "abys",
        name = "Abyssinian",
        weight = WeightAPIModel(imperial = "7 - 10", metric = "3 - 5"),
        cfaUrl = "http://cfa.org/Breeds/BreedsAB/Abyssinian.aspx",
        vetstreetUrl = "http://www.vetstreet.com/cats/abyssinian",
        vcahospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/abyssinian",
        temperament = "Active, Energetic, Independent, Intelligent, Gentle",
        origin = "Egypt",
        countryCodes = "EG",
        countryCode = "EG",
        description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
        lifeSpan = "14 - 15",
        indoor = 0,
        lap = 1,
        altNames = "",
        adaptability = 5,
        affectionLevel = 5,
        childFriendly = 3,
        dogFriendly = 4,
        energyLevel = 5,
        grooming = 1,
        healthIssues = 2,
        intelligence = 5,
        sheddingLevel = 2,
        socialNeeds = 5,
        strangerFriendly = 5,
        vocalisation = 1,
        experimental = 0,
        hairless = 0,
        natural = 1,
        rare = 0,
        rex = 0,
        suppressedTail = 0,
        shortLegs = 0,
        wikipediaUrl = "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
        hypoallergenic = 0,
        referenceImageId = "0XYvRd7oD",
        referenceImage = BreedImageAPIModel("https://en.wikipedia.org/wiki/0XYvRd7oD")
    )

    val breedAPIModel2 = BreedAPIModel(
        id = "aege",
        name = "Aegean",
        weight = WeightAPIModel(imperial = "7 - 10", metric = "3 - 5"),
        cfaUrl = "",
        vetstreetUrl = "http://www.vetstreet.com/cats/aegean",
        vcahospitalsUrl = "",
        temperament = "Affectionate, Social, Intelligent, Playful, Active",
        origin = "Greece",
        countryCodes = "GR",
        countryCode = "GR",
        description = "Native to the Greek islands known as the Cyclades, the Aegean cat is considered a national treasure.",
        lifeSpan = "9 - 12",
        indoor = 0,
        lap = 1,
        altNames = "",
        adaptability = 5,
        affectionLevel = 4,
        childFriendly = 4,
        dogFriendly = 4,
        energyLevel = 3,
        grooming = 3,
        healthIssues = 1,
        intelligence = 5,
        sheddingLevel = 3,
        socialNeeds = 4,
        strangerFriendly = 5,
        vocalisation = 3,
        experimental = 0,
        hairless = 0,
        natural = 1,
        rare = 0,
        rex = 0,
        suppressedTail = 0,
        shortLegs = 0,
        wikipediaUrl = "https://en.wikipedia.org/wiki/Aegean_cat",
        hypoallergenic = 0,
        referenceImageId = "ozEvzdVM-",
        referenceImage = BreedImageAPIModel("https://en.wikipedia.org/wiki/0XYvRd7oD")
    )

    val breedAPIModel3 = BreedAPIModel(
        id = "abob",
        name = "American Bobtail",
        weight = WeightAPIModel(imperial = "7 - 16", metric = "3 - 7"),
        cfaUrl = "http://cfa.org/Breeds/BreedsAB/AmericanBobtail.aspx",
        vetstreetUrl = "http://www.vetstreet.com/cats/american-bobtail",
        vcahospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/american-bobtail",
        temperament = "Intelligent, Interactive, Lively, Playful, Sensitive",
        origin = "United States",
        countryCodes = "US",
        countryCode = "US",
        description = "American Bobtails are loving and intelligent cats, known for their distinctive bobbed tails.",
        lifeSpan = "11 - 15",
        indoor = 0,
        lap = 1,
        altNames = "",
        adaptability = 5,
        affectionLevel = 5,
        childFriendly = 4,
        dogFriendly = 5,
        energyLevel = 3,
        grooming = 3,
        healthIssues = 2,
        intelligence = 5,
        sheddingLevel = 3,
        socialNeeds = 5,
        strangerFriendly = 3,
        vocalisation = 3,
        experimental = 0,
        hairless = 0,
        natural = 0,
        rare = 0,
        rex = 0,
        suppressedTail = 1,
        shortLegs = 0,
        wikipediaUrl = "https://en.wikipedia.org/wiki/American_Bobtail",
        hypoallergenic = 0,
        referenceImageId = "ozEvzdVM-",
        referenceImage = BreedImageAPIModel("https://en.wikipedia.org/wiki/0XYvRd7oD")
    )

    val breedDBModel1 = BreedDBModel(
        id = "abys",
        name = "Abyssinian",
        weightImperial = "7 - 10",
        weightMetric = "3 - 5",
        cfaUrl = "http://cfa.org/Breeds/BreedsAB/Abyssinian.aspx",
        vetstreetUrl = "http://www.vetstreet.com/cats/abyssinian",
        vcahospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/abyssinian",
        temperament = "Active, Energetic, Independent, Intelligent, Gentle",
        origin = "Egypt",
        countryCodes = "EG",
        countryCode = "EG",
        description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
        lifeSpan = "14 - 15",
        indoor = 0,
        lap = 1,
        altNames = "",
        adaptability = 5,
        affectionLevel = 5,
        childFriendly = 3,
        dogFriendly = 4,
        energyLevel = 5,
        grooming = 1,
        healthIssues = 2,
        intelligence = 5,
        sheddingLevel = 2,
        socialNeeds = 5,
        strangerFriendly = 5,
        vocalisation = 1,
        experimental = 0,
        hairless = 0,
        natural = 1,
        rare = 0,
        rex = 0,
        suppressedTail = 0,
        shortLegs = 0,
        wikipediaUrl = "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
        hypoallergenic = 0,
        referenceImageUrl = "0XYvRd7oD"
    )

    val breedDBModel2 = BreedDBModel(
        id = "aege",
        name = "Aegean",
        weightImperial = "7 - 10",
        weightMetric = "3 - 5",
        cfaUrl = "",
        vetstreetUrl = "http://www.vetstreet.com/cats/aegean",
        vcahospitalsUrl = "",
        temperament = "Affectionate, Social, Intelligent, Playful, Active",
        origin = "Greece",
        countryCodes = "GR",
        countryCode = "GR",
        description = "Native to the Greek islands known as the Cyclades, the Aegean cat is considered a national treasure.",
        lifeSpan = "9 - 12",
        indoor = 0,
        lap = 1,
        altNames = "",
        adaptability = 5,
        affectionLevel = 4,
        childFriendly = 4,
        dogFriendly = 4,
        energyLevel = 3,
        grooming = 3,
        healthIssues = 1,
        intelligence = 5,
        sheddingLevel = 3,
        socialNeeds = 4,
        strangerFriendly = 5,
        vocalisation = 3,
        experimental = 0,
        hairless = 0,
        natural = 1,
        rare = 0,
        rex = 0,
        suppressedTail = 0,
        shortLegs = 0,
        wikipediaUrl = "https://en.wikipedia.org/wiki/Aegean_cat",
        hypoallergenic = 0,
        referenceImageUrl = "ozEvzdVM-"
    )

    val breedDBModel3 = BreedDBModel(
        id = "abob",
        name = "American Bobtail",
        weightImperial = "7 - 16",
        weightMetric = "3 - 7",
        cfaUrl = "http://cfa.org/Breeds/BreedsAB/AmericanBobtail.aspx",
        vetstreetUrl = "http://www.vetstreet.com/cats/american-bobtail",
        vcahospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/american-bobtail",
        temperament = "Intelligent, Interactive, Lively, Playful, Sensitive",
        origin = "United States",
        countryCodes = "US",
        countryCode = "US",
        description = "American Bobtails are loving and intelligent cats, known for their distinctive bobbed tails.",
        lifeSpan = "11 - 15",
        indoor = 0,
        lap = 1,
        altNames = "",
        adaptability = 5,
        affectionLevel = 5,
        childFriendly = 4,
        dogFriendly = 5,
        energyLevel = 3,
        grooming = 3,
        healthIssues = 2,
        intelligence = 5,
        sheddingLevel = 3,
        socialNeeds = 5,
        strangerFriendly = 3,
        vocalisation = 3,
        experimental = 0,
        hairless = 0,
        natural = 0,
        rare = 0,
        rex = 0,
        suppressedTail = 1,
        shortLegs = 0,
        wikipediaUrl = "https://en.wikipedia.org/wiki/American_Bobtail",
        hypoallergenic = 0,
        referenceImageUrl = "hBXicehMA"
    )

    val breed1 = Breed(
        id = BreedId("abys"),
        name = "Abyssinian",
        weight = Weight(imperial = "7 - 10", metric = "3 - 5"),
        urls = Urls(
            cfaUrl = "http://cfa.org/Breeds/BreedsAB/Abyssinian.aspx",
            vetstreetUrl = "http://www.vetstreet.com/cats/abyssinian",
            vcahospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/abyssinian"
        ),
        attributes = Attributes(
            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
            origin = "Egypt",
            countryCodes = "EG",
            countryCode = "EG",
            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            lifeSpan = "14 - 15",
            indoor = 0,
            lap = 1,
            altNames = ""
        ),
        characteristics = Characteristics(
            adaptability = 5,
            affectionLevel = 5,
            childFriendly = 3,
            dogFriendly = 4,
            energyLevel = 5,
            grooming = 1,
            healthIssues = 2,
            intelligence = 5,
            sheddingLevel = 2,
            socialNeeds = 5,
            strangerFriendly = 5,
            vocalisation = 1,
            experimental = 0,
            hairless = 0,
            natural = 1,
            rare = 0,
            rex = 0,
            suppressedTail = 0,
            shortLegs = 0
        ),
        wikipediaUrl = "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
        hypoallergenic = 0,
        referenceImageUrl = "0XYvRd7oD",
        isFavorite = true
    )

    val breed2 = Breed(
        id = BreedId("aege"),
        name = "Aegean",
        weight = Weight(imperial = "7 - 10", metric = "3 - 5"),
        urls = Urls(
            cfaUrl = "",
            vetstreetUrl = "http://www.vetstreet.com/cats/aegean",
            vcahospitalsUrl = ""
        ),
        attributes = Attributes(
            temperament = "Affectionate, Social, Intelligent, Playful, Active",
            origin = "Greece",
            countryCodes = "GR",
            countryCode = "GR",
            description = "Native to the Greek islands known as the Cyclades, the Aegean cat is considered a national treasure.",
            lifeSpan = "9 - 12",
            indoor = 0,
            lap = 1,
            altNames = ""
        ),
        characteristics = Characteristics(
            adaptability = 5,
            affectionLevel = 4,
            childFriendly = 4,
            dogFriendly = 4,
            energyLevel = 3,
            grooming = 3,
            healthIssues = 1,
            intelligence = 5,
            sheddingLevel = 3,
            socialNeeds = 4,
            strangerFriendly = 5,
            vocalisation = 3,
            experimental = 0,
            hairless = 0,
            natural = 1,
            rare = 0,
            rex = 0,
            suppressedTail = 0,
            shortLegs = 0
        ),
        wikipediaUrl = "https://en.wikipedia.org/wiki/Aegean_cat",
        hypoallergenic = 0,
        referenceImageUrl = "ozEvzdVM-",
        isFavorite = true
    )

    val breed3 = Breed(
        id = BreedId("abob"),
        name = "American Bobtail",
        weight = Weight(imperial = "7 - 16", metric = "3 - 7"),
        urls = Urls(
            cfaUrl = "http://cfa.org/Breeds/BreedsAB/AmericanBobtail.aspx",
            vetstreetUrl = "http://www.vetstreet.com/cats/american-bobtail",
            vcahospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/american-bobtail"
        ),
        attributes = Attributes(
            temperament = "Intelligent, Interactive, Lively, Playful, Sensitive",
            origin = "United States",
            countryCodes = "US",
            countryCode = "US",
            description = "American Bobtails are loving and intelligent cats, known for their distinctive bobbed tails.",
            lifeSpan = "11 - 15",
            indoor = 0,
            lap = 1,
            altNames = ""
        ),
        characteristics = Characteristics(
            adaptability = 5,
            affectionLevel = 5,
            childFriendly = 4,
            dogFriendly = 5,
            energyLevel = 3,
            grooming = 3,
            healthIssues = 2,
            intelligence = 5,
            sheddingLevel = 3,
            socialNeeds = 5,
            strangerFriendly = 3,
            vocalisation = 3,
            experimental = 0,
            hairless = 0,
            natural = 0,
            rare = 0,
            rex = 0,
            suppressedTail = 1,
            shortLegs = 0
        ),
        wikipediaUrl = "https://en.wikipedia.org/wiki/American_Bobtail",
        hypoallergenic = 0,
        referenceImageUrl = "hBXicehMA",
        isFavorite = true
    )

    val breedAPIModelList = listOf(breedAPIModel1, breedAPIModel2, breedAPIModel3)
    val breedDBModelList = listOf(breedDBModel1, breedDBModel2, breedDBModel3)
    val breedList = listOf(breed1, breed2, breed3)
}
