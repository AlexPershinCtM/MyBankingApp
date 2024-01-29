package com.alexpershin.feed.domain.usecase

import com.alexpershin.feed.domain.model.Feed

internal interface GetFeedUseCase {
    suspend fun execute(): Result<List<Feed>>
}