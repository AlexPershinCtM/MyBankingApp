package com.alexpershin.feed.domain.usecase.impl

import com.alexpershin.core.preferences.SecurePreferences
import com.alexpershin.feed.domain.model.Feed
import com.alexpershin.feed.domain.repository.FeedRepository
import com.alexpershin.feed.domain.usecase.GetFeedUseCase
import javax.inject.Inject

internal class GetFeedUseCaseImpl @Inject constructor(
    private val repository: FeedRepository,
    private val securePreferences: SecurePreferences,
) : GetFeedUseCase {

    override suspend fun execute(): Result<List<Feed>> {
        val accountUid = securePreferences.accountUid
        val categoryUid = securePreferences.categoryUid

        return repository.fetchFeed(
            accountUid = accountUid,
            categoryUid = categoryUid,
        )
    }
}
