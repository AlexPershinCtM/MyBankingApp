package com.alexpershin.feed.domain.repository

import com.alexpershin.feed.domain.model.Feed

interface FeedRepository {
    suspend fun fetchFeed(
        accountUid: String,
        categoryUid: String,
    ): Result<List<Feed>>
}