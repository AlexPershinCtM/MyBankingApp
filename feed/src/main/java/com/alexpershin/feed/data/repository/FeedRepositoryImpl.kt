package com.alexpershin.feed.data.repository

import com.alexpershin.core.network.StarlingException
import com.alexpershin.feed.data.api.FeedService
import com.alexpershin.feed.data.mapper.FeedDtoMapper
import com.alexpershin.feed.domain.model.Feed
import com.alexpershin.feed.domain.repository.FeedRepository
import javax.inject.Inject

internal class FeedRepositoryImpl @Inject constructor(
    private val feedService: FeedService,
    private val feedDtoMapper: FeedDtoMapper
) : FeedRepository {
    override suspend fun fetchFeed(
        accountUid: String,
        categoryUid: String,
    ): Result<List<Feed>> {
        return try {
            val result = feedService.fetchFeed(
                accountUid = accountUid,
                categoryUid = categoryUid,
            )

            if (result.isSuccessful) {
                val items = requireNotNull(result.body()).feedItems
                Result.success(
                    items.map { feedDtoMapper.map(it) }
                )
            } else {
                Result.failure(StarlingException.HttpException(result.code()))
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
