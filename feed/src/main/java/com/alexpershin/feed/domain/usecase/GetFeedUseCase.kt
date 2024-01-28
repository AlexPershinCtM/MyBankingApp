package com.alexpershin.feed.domain.usecase

import com.alexpershin.feed.domain.model.Feed

interface GetFeedUseCase {
    suspend fun execute(): Result<List<Feed>>
}