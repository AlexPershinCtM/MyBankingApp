package com.alexpershin.feed.data.model

import androidx.annotation.Keep

@Keep
data class FeedResponse(
    val feedItems: List<FeedDto>
) {
    data class FeedDto(
        val amount: AmountDto,
        val counterPartyName: String,
        val direction: String,
        val feedItemUid: String,
        val spendingCategory: String,
        val transactionTime: String,
    ) {
        data class AmountDto(
            val currency: String,
            val minorUnits: Long
        )
    }
}