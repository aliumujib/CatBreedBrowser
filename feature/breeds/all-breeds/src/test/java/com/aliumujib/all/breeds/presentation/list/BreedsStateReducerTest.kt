package com.aliumujib.all.breeds.presentation.list

import com.aliumujib.common.test.SharedDummyData

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class BreedsStateReducerTest {

    private val reducer: BreedsStateReducer = BreedsStateReducer()

    @Test
    fun `given initial state, when FetchCatBreedsResult Loading, then state is Loading`() {
        val initialState = BreedsContract.BreedsUiState.Initial
        val result = BreedsContract.BreedsResult.FetchCatBreedsResult.Loading

        val newState = reducer.reduce(initialState, result)

        assertThat(newState).isEqualTo(BreedsContract.BreedsUiState.Loading)
    }

    @Test
    fun `given initial state, when FetchCatBreedsResult Error, then state is Error`() {
        val initialState = BreedsContract.BreedsUiState.Initial
        val error = Throwable("Error fetching breeds")
        val result = BreedsContract.BreedsResult.FetchCatBreedsResult.Error(error)

        val newState = reducer.reduce(initialState, result)

        assertThat(newState).isEqualTo(BreedsContract.BreedsUiState.Error(error.message))
    }

    @Test
    fun `given initial state, when FetchCatBreedsResult Success with data, then state is Success`() {
        val initialState = BreedsContract.BreedsUiState.Initial
        val breeds = SharedDummyData.breedList
        val result = BreedsContract.BreedsResult.FetchCatBreedsResult.Success(breeds)

        val newState = reducer.reduce(initialState, result)

        assertThat(newState).isEqualTo(BreedsContract.BreedsUiState.Success(breeds))
    }

    @Test
    fun `given initial state, when FetchCatBreedsResult Success with empty data, then state is Empty`() {
        val initialState = BreedsContract.BreedsUiState.Initial
        val result = BreedsContract.BreedsResult.FetchCatBreedsResult.Success(emptyList())

        val newState = reducer.reduce(initialState, result)

        assertThat(newState).isEqualTo(BreedsContract.BreedsUiState.Empty)
    }

    @Test
    fun `given success state, when ToggleFavouriteStatusResult Success, then update breed favorite status`() {
        val breeds = SharedDummyData.breedList
        val breedId = breeds.first().id
        val initialState = BreedsContract.BreedsUiState.Success(breeds)
        val result = BreedsContract.BreedsResult.ToggleFavouriteStatusResult.Success(breedId)

        val newState = reducer.reduce(initialState, result)

        val updatedBreeds = breeds.map {
            if (it.id == breedId) it.copy(isFavorite = !it.isFavorite) else it
        }

        assertThat(newState).isEqualTo(BreedsContract.BreedsUiState.Success(updatedBreeds))
    }

    @Test
    fun `given success state, when ToggleFavouriteStatusResult Error, then state is Error`() {
        val breeds = SharedDummyData.breedList

        val initialState = BreedsContract.BreedsUiState.Success(breeds)
        val error = Throwable("Error toggling favorite status")
        val result = BreedsContract.BreedsResult.ToggleFavouriteStatusResult.Error(error)

        val newState = reducer.reduce(initialState, result)

        assertThat(newState).isEqualTo(BreedsContract.BreedsUiState.Error(error.message))
    }
}
