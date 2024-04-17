package com.alexpershin.feed.presentation

import com.alexpershin.core.logging.ErrorLogger
import com.alexpershin.feed.R
import com.alexpershin.feed.domain.model.Feed
import com.alexpershin.feed.domain.usecase.GetFeedUseCase
import com.alexpershin.feed.domain.usecase.RoundUpUseCase
import com.alexpershin.feed.presentation.mapper.FeedUiModelMapper
import com.alexpershin.feed.presentation.model.FeedUiModel
import com.alexpershin.navigation.domain.CoreNavigationDestinations
import com.alexpershin.navigation.domain.NavigationCommand
import com.alexpershin.navigation.domain.NavigationHandler
import com.alexpershin.test.CoroutineTestExtension
import com.alexpershin.test.TestException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
internal class FeedViewModelTest {

    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @JvmField
    @RegisterExtension
    val coroutineTestExtension = CoroutineTestExtension(testDispatcher)

    private val getFeedUseCase: GetFeedUseCase = mockk(relaxed = true)
    private val roundUpUseCase: RoundUpUseCase = mockk(relaxed = true)
    private val feedUiModelMapper: FeedUiModelMapper = mockk(relaxed = true)
    private val navigationHandler: NavigationHandler = mockk(relaxed = true)
    private val errorLogger: ErrorLogger = mockk(relaxed = true)

    private lateinit var sut: FeedViewModel

    private fun createSut() {
        sut = FeedViewModel(
            getFeedUseCase = getFeedUseCase,
            roundUpUseCase = roundUpUseCase,
            feedUiModelMapper = feedUiModelMapper,
            navigationHandler = navigationHandler,
            errorLogger = errorLogger,
        )
    }

    @Test
    fun `GIVEN sut is created, WHEN getFeedUseCase returns result, THEN update uiState`() =
        runTest {
            // given
            val feedList = listOf<Feed>(mockk(relaxed = true), mockk(relaxed = true))
            val feedUiModels = listOf<FeedUiModel>(mockk(relaxed = true), mockk(relaxed = true))

            coEvery { getFeedUseCase.execute() } returns Result.success(feedList)

            feedList.forEachIndexed { index, item ->
                every { feedUiModelMapper.map(item) } returns feedUiModels[index]
            }

            coEvery { roundUpUseCase.execute(any()) } returns 2550

            // when
            createSut()
            sut.onUiEvent(FeedUiContract.UiEvents.OnResume)
            advanceUntilIdle()

            // then
            with(sut.uiState.value) {
                assertFalse(isLoading)
                assertEquals("25.50", roundUpAmount)
                assertEquals(feedUiModels, feedItems)
                assertNull(errorMessage)
            }
        }

    @Test
    fun `GIVEN sut is created, WHEN getFeedUseCase returns empty result, THEN update uiState`() =
        runTest {
            // given
            val feedEmptyList = emptyList<Feed>()

            coEvery { getFeedUseCase.execute() } returns Result.success(feedEmptyList)

            // when
            createSut()
            sut.onUiEvent(FeedUiContract.UiEvents.OnResume)
            advanceUntilIdle()

            // then
            with(sut.uiState.value) {
                assertFalse(isLoading)
                assertEquals(emptyList<FeedUiModel>(), feedItems)
                assertNull(errorMessage)
            }
        }


    @Test
    fun `GIVEN sut is created, WHEN getFeedUseCase returns TestException, THEN display generic error`() =
        runTest {
            // given
            val exception = TestException

            coEvery { getFeedUseCase.execute() } returns Result.failure(exception)

            // when
            createSut()
            sut.onUiEvent(FeedUiContract.UiEvents.OnResume)
            advanceUntilIdle()

            // then
            val expectedError = R.string.generic_error

            with(sut.uiState.value) {
                assertFalse(isLoading)
                assertEquals(expectedError, errorMessage)
            }
        }

    @Test
    fun `GIVEN sut is created, WHEN getFeedUseCase returns UnknownHostException, THEN display no internet error`() =
        runTest {
            // given
            val exception = UnknownHostException()

            coEvery { getFeedUseCase.execute() } returns Result.failure(exception)

            // when
            createSut()
            sut.onUiEvent(FeedUiContract.UiEvents.OnResume)
            advanceUntilIdle()

            // then
            val expectedError = R.string.error_no_internet

            with(sut.uiState.value) {
                assertFalse(isLoading)
                assertEquals(expectedError, errorMessage)
            }
        }


    @Test
    fun `GIVEN sut is created, WHEN PullToRefresh event received, call getFeedUseCase`() =
        runTest {
            // given
            createSut()

            // when
            sut.onUiEvent(FeedUiContract.UiEvents.PullToRefresh)
            advanceUntilIdle()

            // then
            coVerify {
                getFeedUseCase.execute()
            }
        }

    @Test
    fun `GIVEN sut is created, WHEN RetryClicked event received, THEN call getFeedUseCase`() =
        runTest {
            // given
            createSut()

            // when
            sut.onUiEvent(FeedUiContract.UiEvents.RetryClicked)
            advanceUntilIdle()

            // then
            coVerify {
                getFeedUseCase.execute()
            }
        }

    @Test
    fun `GIVEN sut is created, WHEN BackPressed event received, THEN call navigator`() =
        runTest {
            // given
            createSut()

            // when
            sut.onUiEvent(FeedUiContract.UiEvents.BackPressed)
            advanceUntilIdle()

            // then
            coVerify {
                navigationHandler.navigate(NavigationCommand.Back)
            }
        }

    @Test
    fun `GIVEN sut is created, WHEN RoundUpBannerClicked event received, THEN call navigator`() =
        runTest {
            // given
            val amountToRoundUp = 2432L
            val feedList = listOf<Feed>(mockk(relaxed = true), mockk(relaxed = true))

            coEvery { getFeedUseCase.execute() } returns Result.success(feedList)
            coEvery { roundUpUseCase.execute(any()) } returns amountToRoundUp

            createSut()
            sut.onUiEvent(FeedUiContract.UiEvents.OnResume)
            advanceUntilIdle()


            // when
            sut.onUiEvent(FeedUiContract.UiEvents.RoundUpBannerClicked)
            advanceUntilIdle()

            // then
            coVerify {
                navigationHandler.navigate(
                    CoreNavigationDestinations.SavingGoals(amountToRoundUp)
                )
            }
        }
}