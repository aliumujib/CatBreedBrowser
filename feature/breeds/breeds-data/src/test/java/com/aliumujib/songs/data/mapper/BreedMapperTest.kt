package com.aliumujib.songs.data.mapper

import com.aliumujib.songs.data.TestDummyData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class BreedMapperTest {

    private val mapper: BreedMapper = BreedMapper()


    @Test
    fun `given DBModel when mapToUIModel is called, then it should return valid Breed`() {
        val dbModel = TestDummyData.breedDBModel1
        val isFavorite = true

        val result = mapper.mapToUIModel(dbModel, isFavorite)

        with(result) {
            assertThat(id.data).isEqualTo(dbModel.id)
            assertThat(name).isEqualTo(dbModel.name)
            assertThat(weight.imperial).isEqualTo(dbModel.weightImperial)
            assertThat(weight.metric).isEqualTo(dbModel.weightMetric)
            assertThat(urls.cfaUrl).isEqualTo(dbModel.cfaUrl)
            assertThat(urls.vetstreetUrl).isEqualTo(dbModel.vetstreetUrl)
            assertThat(urls.vcahospitalsUrl).isEqualTo(dbModel.vcahospitalsUrl)
            assertThat(attributes.temperament).isEqualTo(dbModel.temperament)
            assertThat(characteristics.adaptability).isEqualTo(dbModel.adaptability)
            assertThat(isFavorite).isTrue()
        }
    }

    @Test
    fun `given APIModel when mapToDBModel is called, then it should return valid BreedDBModel`() {
        val apiModel = TestDummyData.breedAPIModel1

        val result = mapper.mapToDBModel(apiModel)

        with(result) {
            assertThat(id).isEqualTo(apiModel.id)
            assertThat(name).isEqualTo(apiModel.name)
            assertThat(weightImperial).isEqualTo(apiModel.weight.imperial)
            assertThat(weightMetric).isEqualTo(apiModel.weight.metric)
            assertThat(cfaUrl).isEqualTo(apiModel.cfaUrl)
            assertThat(vetstreetUrl).isEqualTo(apiModel.vetstreetUrl)
            assertThat(vcahospitalsUrl).isEqualTo(apiModel.vcahospitalsUrl)
            assertThat(temperament).isEqualTo(apiModel.temperament)
            assertThat(apiModel.adaptability).isEqualTo(apiModel.adaptability)
            assertThat(referenceImageUrl).isEqualTo(apiModel.referenceImage?.imageUrl)
        }
    }
}