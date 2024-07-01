package com.aliumujib.breed.common.ui

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
class BreedListItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val onItemClick = mockk<(Breed) -> Unit>(relaxed = true)
    private val onMoreClick = mockk<(Breed) -> Unit>(relaxed = true)

    @Test
    fun breedListItem_displaysCorrectData() {
        val breed = SharedDummyData.breed1
        composeTestRule.setContent {
            BreedListItem(
                breed = breed,
                onItemClick = {},
                onMoreClick = {}
            )
        }

        composeTestRule.onNodeWithText(breed.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(breed.attributes.description).assertIsDisplayed()
    }

    @Test
    fun breedListItem_whenClicked_invokesOnItemClick() {
        val breed = SharedDummyData.breed1
        composeTestRule.setContent {
            BreedListItem(
                breed = breed,
                onItemClick = onItemClick,
                onMoreClick = onMoreClick
            )
        }

        composeTestRule.onNodeWithText("Abyssinian").performClick()
        coVerify { onItemClick(breed) }
    }

    @Test
    fun breedListItem_whenMoreClicked_invokesOnMoreClick() {
        val breed = SharedDummyData.breed1
        composeTestRule.setContent {
            BreedListItem(
                breed = breed,
                onItemClick = onItemClick,
                onMoreClick = onMoreClick
            )
        }

        composeTestRule.onNodeWithContentDescription("More").performClick()
        coVerify { onMoreClick(breed) }
    }
}