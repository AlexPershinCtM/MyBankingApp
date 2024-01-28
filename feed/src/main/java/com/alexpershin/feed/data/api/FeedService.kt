package com.alexpershin.feed.data.api

import com.alexpershin.feed.data.model.FeedResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface FeedService {

    @GET("/api/v2/feed/account/{$ACCOUNT_UID_PARAM}/category/{$CATEGORY_UID_PARAM}?changesSince=2023-11-27T08:49:17.037Z")
    fun fetchFeed(
        @Path(ACCOUNT_UID_PARAM) accountUid: String,
        @Path(CATEGORY_UID_PARAM) categoryUid: String,
    ): Deferred<Response<FeedResponse>>

    private companion object {
        private const val ACCOUNT_UID_PARAM = "accountUid"
        private const val CATEGORY_UID_PARAM = "categoryUid"
    }
}