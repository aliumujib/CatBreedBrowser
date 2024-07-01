package com.aliumujib.all.cats.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aliumujib.all.breeds.presentation.list.BreedsContract
import com.aliumujib.all.breeds.ui.BreedsScreenContent
import com.aliumujib.common.test.SharedDummyData
import com.aliumujib.designsystem.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BreedScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun breedsScreenContent_whenLoading_showsLoadingIndicator() {
        composeTestRule.setContent {
            AppTheme {
                BreedsScreenContent(
                    isMoreSheetOpen = false,
                    uiState = BreedsContract.BreedsUiState.Loading,
                    focusedBreed = null,
                    onItemClicked = {},
                    onMoreClick = {},
                    onMoreDismissedRequest = {},
                    onFavoriteClick = {}
                )
            }
        }
        composeTestRule.onNodeWithTag("Loading").assertIsDisplayed()
    }

    @Test
    fun breedsScreenContent_whenErrorState_showsErrorMessage() {
        val errorMessage = "Network Error"
        composeTestRule.setContent {
            AppTheme {
                BreedsScreenContent(
                    isMoreSheetOpen = false,
                    uiState = BreedsContract.BreedsUiState.Error(errorMessage),
                    focusedBreed = null,
                    onItemClicked = {},
                    onMoreClick = {},
                    onMoreDismissedRequest = {},
                    onFavoriteClick = {}
                )
            }
        }
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun breedsScreenContent_whenSuccessState_breedsDisplayed() {
        val breeds = SharedDummyData.breedList
        composeTestRule.setContent {
            AppTheme {
                BreedsScreenContent(
                    isMoreSheetOpen = false,
                    uiState = BreedsContract.BreedsUiState.Success(breeds),
                    focusedBreed = null,
                    onItemClicked = {},
                    onMoreClick = {},
                    onMoreDismissedRequest = {},
                    onFavoriteClick = {}
                )
            }
        }

        breeds.forEach { breed ->
            composeTestRule.onNodeWithText(breed.name).assertIsDisplayed()
        }
    }

    @Test
    fun breedsScreenContent_whenEmptyState_showsNoBreedsFoundMessage() {
        composeTestRule.setContent {
            AppTheme {
                BreedsScreenContent(
                    isMoreSheetOpen = false,
                    uiState = BreedsContract.BreedsUiState.Empty,
                    focusedBreed = null,
                    onItemClicked = {},
                    onMoreClick = {},
                    onMoreDismissedRequest = {},
                    onFavoriteClick = {}
                )
            }
        }
        composeTestRule.onNodeWithText("No breeds found!").assertIsDisplayed()
    }

}