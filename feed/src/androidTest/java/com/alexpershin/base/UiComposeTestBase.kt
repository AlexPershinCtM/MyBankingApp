package com.alexpershin.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.alexpershin.ui.theme.MyStarlingAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule

// TODO extract into common:ui-test

@HiltAndroidTest
open class UiComposeTestBase() {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)


    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    open fun setContent(content: @Composable () -> Unit) {
        composeTestRule.setContent {
            MyStarlingAppTheme {
                content()
            }
        }
    }

}