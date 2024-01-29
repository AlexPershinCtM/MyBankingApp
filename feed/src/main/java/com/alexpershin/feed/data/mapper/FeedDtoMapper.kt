package com.alexpershin.feed.data.mapper

import com.alexpershin.feed.data.model.FeedDto
import com.alexpershin.feed.domain.model.Feed
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.inject.Inject


internal class FeedDtoMapper @Inject constructor() {

    fun map(dto: FeedDto): Feed {
        return Feed(
            id = dto.feedItemUid,
            amount = dto.amount.minorUnits / 100.0,
            currency = dto.amount.currency,
            direction = mapDirection(dto.direction),
            merchant = dto.counterPartyName,
            category = mapCategory(dto.spendingCategory),
            dateTime = mapDateTime(dto.transactionTime)
        )
    }

    private fun mapDirection(direction: String): Feed.Direction {
        return when (direction.lowercase()) {
            "in" -> Feed.Direction.In
            "out" -> Feed.Direction.Out
            else -> throw IllegalStateException("direction can be only one of ['IN','OUT']. Received: direction:{$direction}")
        }
    }

    /**
     * Ideally this logic should be moved to separate class
     * */
    private fun mapDateTime(transactionTime: String): LocalDateTime {
        val zdt = ZonedDateTime.parse(transactionTime)

        return zdt.toLocalDateTime()
    }

    private fun mapCategory(spendingCategory: String): Feed.Category {
        return when (spendingCategory) {
            "SAVING" -> Feed.Category.Savings
            "PAYMENTS" -> Feed.Category.Payments
            "INCOME" -> Feed.Category.Income
            else -> Feed.Category.Unknown
        }
    }

}
