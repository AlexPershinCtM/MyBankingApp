package com.alexpershin.robolectric.feed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performSemanticsAction
import com.alexpershin.feed.R
import com.alexpershin.feed.presentation.FeedUiContract
import com.alexpershin.feed.presentation.FeedUiContract.UiState
import com.alexpershin.feed.presentation.model.FeedUiModel
import com.alexpershin.feed.presentation.ui.FeedScreen
import com.alexpershin.feed.presentation.ui.FeedScreenTestTags
import com.alexpershin.robolectric.RobolectricComposeBase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FeedScreenRobolectricTest : RobolectricComposeBase() {

    private val mockViewModel: FeedUiContract.ViewModel = mockk(relaxed = true)

    private fun givenUiStateReturns(uiState: UiState = defaultUiState) {
        every { mockViewModel.uiState } returns MutableStateFlow(uiState)
    }

    @Test
    fun `GIVEN  FeedScreen with items, WHEN item clicked, THEN assert ItemClicked event received`() {
        // given
        val clickedId = "1"
        val ids = (1..4).map { it.toString() }
        val uiModels = createModels(ids)

        givenUiStateReturns(defaultUiState.copy(feedItems = uiModels))

        setContent {
            FeedScreen(
                viewModel = mockViewModel
            )
        }

        // when
        composeTestRule.onNodeWithText(clickedId).click()

        // then
        verify {
            mockViewModel.onUiEvent(FeedUiContract.UiEvents.ItemClicked(clickedId))
        }
    }

    @Test
    fun `GIVEN  FeedScreen with items, WHEN Banner clicked, THEN assert RoundUpBannerClicked event received`() {
        // given
        val ids = (1..4).map { it.toString() }
        val uiModels = createModels(ids)

        givenUiStateReturns(defaultUiState.copy(feedItems = uiModels))

        setContent {
            FeedScreen(
                viewModel = mockViewModel
            )
        }

        // when
        composeTestRule.onNodeWithTag(FeedScreenTestTags.RoundUpBanner).click()

        // then
        verify {
            mockViewModel.onUiEvent(FeedUiContract.UiEvents.RoundUpBannerClicked)
        }
    }


    @Test
    fun `GIVEN  FeedScreen with error, WHEN retry button clicked, THEN assert RetryClicked event received`() {
        // given
        givenUiStateReturns(defaultUiState.copy(errorMessage = R.string.error_no_internet))

        setContent {
            FeedScreen(
                viewModel = mockViewModel
            )
        }

        // when
        composeTestRule.onNodeWithTag(FeedScreenTestTags.TryAgainButton).click()

        // then
        verify {
            mockViewModel.onUiEvent(FeedUiContract.UiEvents.RetryClicked)
        }
    }


    private fun createModels(ids: List<String>): ImmutableList<FeedUiModel> {
        return ids.map { id ->
            FeedUiModel(
                id = id,
                icon = Icons.Rounded.Warning,
                amount = "",
                currency = FeedUiModel.CurrencySymbol.GBP,
                sign = FeedUiModel.Sign.Minus,
                merchant = id,
                dateTime = ""
            )
        }.toPersistentList()
    }

    private companion object {
        private val defaultUiState = UiState(
            roundUpAmount = "",
            isLoading = false,
            errorMessage = null,
            feedItems = persistentListOf()
        )
    }

}

fun SemanticsNodeInteraction.click() {
    performSemanticsAction(SemanticsActions.OnClick)
}