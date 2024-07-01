package com.aliumujib.breed.details.presentation

import com.aliumujib.common.test.SharedDummyData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class BreedDetailsStateReducerTest {

    private val reducer: BreedDetailsStateReducer = BreedDetailsStateReducer()

    @Test
    fun `given initial state, when FetchCatBreedDetailsResult Loading, then state is Loading`() {
        val initialState = BreedDetailsContract.BreedDetailsUiState.Initial
        val result = BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Loading

        val newState = reducer.reduce(initialState, result)

        assertThat(newState).isEqualTo(BreedDetailsContract.BreedDetailsUiState.Loading)
    }

    @Test
    fun `given initial state, when FetchCatBreedDetailsResult Success, then state is Success`() {
        val initialState = BreedDetailsContract.BreedDetailsUiState.Initial
        val breed = SharedDummyData.breed1
        val result = BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Success(breed)

        val newState = reducer.reduce(initialState, result)

        assertThat(newState).isEqualTo(BreedDetailsContract.BreedDetailsUiState.Success(breed))
    }

    @Test
    fun `given initial state, when FetchCatBreedDetailsResult Error, then state is Error`() {
        val initialState = BreedDetailsContract.BreedDetailsUiState.Initial
        val error = Throwable("Error fetching breed details")
        val result = BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Error(error)

        val newState = reducer.reduce(initialState, result)

        assertThat(newState).isEqualTo(BreedDetailsContract.BreedDetailsUiState.Error(error.message))
    }

    @Test
    fun `given initial state, when ToggleFavouriteStatusResult Error, then state is Error`() {
        val initialState = BreedDetailsContract.BreedDetailsUiState.Initial
        val error = Throwable("Error toggling favourite status")
        val result = BreedDetailsContract.BreedDetailsResult.ToggleFavouriteStatusResult.Error(error)

        val newState = reducer.reduce(initialState, result)

        assertThat(newState).isEqualTo(BreedDetailsContract.BreedDetailsUiState.Error(error.message))
    }

    @Test
    fun `given initial state, when ToggleFavouriteStatusResult Success, then state is Initial`() {
        val initialState = BreedDetailsContract.BreedDetailsUiState.Initial
        val breedId = SharedDummyData.breed1.id
        val result = BreedDetailsContract.BreedDetailsResult.ToggleFavouriteStatusResult.Success(breedId)

        val newState = reducer.reduce(initialState, result)

        assertThat(newState).isEqualTo(BreedDetailsContract.BreedDetailsUiState.Initial)
    }
}
