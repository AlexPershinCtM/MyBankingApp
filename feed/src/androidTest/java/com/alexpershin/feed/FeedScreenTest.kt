package com.alexpershin.feed

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.alexpershin.base.UiComposeTestBase
import com.alexpershin.base.value
import com.alexpershin.core.logging.ErrorLogger
import com.alexpershin.feed.domain.model.Feed
import com.alexpershin.feed.domain.usecase.GetFeedUseCase
import com.alexpershin.feed.domain.usecase.RoundUpUseCase
import com.alexpershin.feed.presentation.FeedViewModel
import com.alexpershin.feed.presentation.mapper.FeedUiModelMapper
import com.alexpershin.feed.presentation.ui.FeedScreen
import com.alexpershin.feed.presentation.ui.FeedScreenTestTags
import com.alexpershin.navigation.domain.NavigationHandler
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import kotlinx.collections.immutable.toPersistentList
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

@HiltAndroidTest
class FeedScreenTest : UiComposeTestBase() {

    @Inject
    internal lateinit var getFeedUseCase: GetFeedUseCase

    @Inject
    internal lateinit var roundUpUseCase: RoundUpUseCase

    @Inject
    internal lateinit var feedUiModelMapper: FeedUiModelMapper

    @Inject
    lateinit var navigationHandler: NavigationHandler

    @Inject
    lateinit var errorLogger: ErrorLogger

    private lateinit var viewModel: FeedViewModel

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    private fun createViewModel() {
        viewModel = FeedViewModel(
            getFeedUseCase = getFeedUseCase,
            roundUpUseCase = roundUpUseCase,
            feedUiModelMapper = feedUiModelMapper,
            navigationHandler = navigationHandler,
            errorLogger = errorLogger
        )
    }

    @Test
    fun GIVEN_FeedScreen_with_generic_error_THEN_assert_error_displayed() {
        // given
        val errorMessage = "Oooops, something went wrong, try again!"
        coEvery { getFeedUseCase.execute() } returns Result.failure(Exception())

        createViewModel()

        // when
        composeTestRule.setContent {
            FeedScreen(viewModel)
        }

        // then
        composeTestRule.onNodeWithTag(FeedScreenTestTags.TryAgainButton).assertIsDisplayed()
        composeTestRule.onNodeWithText(text = errorMessage, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun GIVEN_FeedScreen_with_no_internet_error_THEN_assert_error_displayed() {
        // given
        val errorMessage = "It seems that you have no internet connection, try again!"
        coEvery { getFeedUseCase.execute() } returns value(Result.failure(UnknownHostException()))

        createViewModel()

        // when
        composeTestRule.setContent {
            FeedScreen(viewModel)
        }

        // then
        composeTestRule.onNodeWithTag(FeedScreenTestTags.TryAgainButton).assertIsDisplayed()
        composeTestRule.onNodeWithText(text = errorMessage, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun GIVEN_FeedScreen_with_items_THEN_assert_round_up_banner_displayed() {
        // given
        val weekRoundUp = 25.23
        coEvery { getFeedUseCase.execute() } returns value(Result.success(feedItems))
        coEvery { roundUpUseCase.execute(any()) } returns value(weekRoundUp)

        createViewModel()

        // when
        composeTestRule.setContent {
            FeedScreen(viewModel)
        }

        // then
        composeTestRule.onNodeWithTag(FeedScreenTestTags.RoundUpBanner).assertIsDisplayed()
        composeTestRule.onNodeWithText("Transfer weekly Round Up amount of £25.23 to your saving goal!").assertIsDisplayed()
    }


    private companion object {
        private val feedItems: List<Feed> = (1..5).map {
            Feed(
                id = it.toString(),
                amount = Random.nextDouble(),
                currency = "GBP",
                direction = Feed.Direction.In,
                merchant = "Asda",
                category =Feed.Category.Payments,
                dateTime = LocalDateTime.now()
            )
        }.toPersistentList()
    }
}