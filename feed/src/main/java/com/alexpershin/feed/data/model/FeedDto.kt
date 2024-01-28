package com.alexpershin.feed.data.model

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
