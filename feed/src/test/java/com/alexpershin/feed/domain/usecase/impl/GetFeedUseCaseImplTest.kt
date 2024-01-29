package com.alexpershin.feed.domain.usecase.impl

import com.alexpershin.core.preferences.SecurePreferences
import com.alexpershin.feed.domain.model.Feed
import com.alexpershin.feed.domain.repository.FeedRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GetFeedUseCaseImplTest {

    private val repository: FeedRepository = mockk(relaxed = true)
    private val securePreferences: SecurePreferences = mockk(relaxed = true)

    private val sut = GetFeedUseCaseImpl(
        repository = repository,
        securePreferences = securePreferences,
    )

    @Test
    fun `WHEN_execute is called and FeedRepository returns error, THEN return error`() = runTest {
        // given
        val expected = IllegalArgumentException()

        coEvery { repository.fetchFeed(any(), any()) } returns Result.failure(expected)

        // when
        val result = sut.execute()

        // then
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `WHEN_execute is called and FeedRepository returns success, THEN return success`() =
        runTest {
            // given
            val feedList = listOf<Feed>(mockk(relaxed = true), mockk(relaxed = true))

            coEvery { repository.fetchFeed(any(), any()) } returns Result.success(feedList)

            // when
            val result = sut.execute()

            // then
            assertEquals(
                Result.success(feedList),
                result
            )
        }
}