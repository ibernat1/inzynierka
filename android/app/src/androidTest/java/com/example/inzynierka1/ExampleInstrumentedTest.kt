package com.example.inzynierka1

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inzynierka1.ui.screens.MainActivityScreen
import com.example.inzynierka1.viewmodels.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun buttonDisappearsAfterClick() {
        val viewModel = MainViewModel() // Assuming you have a way to create your ViewModel
        composeTestRule.setContent {
            MainActivityScreen(viewModel = viewModel)
        }

        // Initial state should have the button
        composeTestRule.onNodeWithText("Standing Button Text").assertExists()

        // Perform click on the button
        composeTestRule.onNodeWithText("Standing Button Text").performClick()

        // Button should disappear after click
        composeTestRule.onNodeWithText("Standing Button Text").assertDoesNotExist()
    }
}