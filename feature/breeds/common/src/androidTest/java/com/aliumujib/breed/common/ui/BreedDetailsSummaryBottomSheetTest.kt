package com.aliumujib.breed.common.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aliumujib.common.test.SharedDummyData
import com.aliumujib.model.Breed
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
class BreedDetailsSummaryBottomSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val onDismissRequest = mockk<() -> Unit>(relaxed = true)
    private val onFavoriteClick = mockk<(Breed) -> Unit>(relaxed = true)

    @Test
    fun breedDetailsSummaryBottomSheet_displaysDetailsAndHandlesInteractions() {
        val sheetState = mockk<SheetState>(relaxed = true)
        val breed = SharedDummyData.breed1

        composeTestRule.setContent {
            BreedDetailsSummaryBottomSheet(
                breed = breed,
                sheetState = sheetState,
                onDismissRequest = onDismissRequest,
                onFavoriteClick = onFavoriteClick
            )
        }

        composeTestRule.onNodeWithText(breed.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(breed.attributes.description).assertIsDisplayed()
        composeTestRule.onNodeWithText("Active, Energetic").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Heart").performClick()
        coVerify { onFavoriteClick(breed) }
    }

}