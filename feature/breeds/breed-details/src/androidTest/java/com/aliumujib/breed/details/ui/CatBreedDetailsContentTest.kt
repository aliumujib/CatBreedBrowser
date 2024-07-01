package com.aliumujib.breed.details.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aliumujib.breed.details.presentation.BreedDetailsContract
import com.aliumujib.common.test.SharedDummyData
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatBreedDetailsContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun catBreedDetailsContent_displaysSuccessStateCorrectly() {
        val breed = SharedDummyData.breed1
        val state = BreedDetailsContract.BreedDetailsUiState.Success(breed)

        composeTestRule.setContent {
            CatBreedDetailsContent(
                state = state,
                onNavigateUp = {}
            )
        }

        composeTestRule.onNodeWithText("Abyssinian").assertIsDisplayed()
        composeTestRule.onNodeWithText("Very playful and active").assertIsDisplayed()
    }

    @Test
    fun catBreedDetailsContent_displaysLoadingState() {
        composeTestRule.setContent {
            CatBreedDetailsContent(
                state = BreedDetailsContract.BreedDetailsUiState.Loading,
                onNavigateUp = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Progress Indicator").assertIsDisplayed()
    }

    @Test
    fun catBreedDetailsContent_displaysErrorState() {
        val errorMessage = "Network Error"
        composeTestRule.setContent {
            CatBreedDetailsContent(
                state = BreedDetailsContract.BreedDetailsUiState.Error(errorMessage),
                onNavigateUp = {}
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
}
