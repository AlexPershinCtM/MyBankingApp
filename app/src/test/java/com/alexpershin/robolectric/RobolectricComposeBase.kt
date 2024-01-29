package com.alexpershin.robolectric

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.alexpershin.ui.theme.MyStarlingAppTheme
import org.junit.Rule

open class RobolectricComposeBase() {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    open fun setContent(content: @Composable () -> Unit) {
        composeTestRule.setContent {
            MyStarlingAppTheme {
                content()
            }
        }
    }

}