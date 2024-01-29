package com.alexpershin.feed.data.mapper

import com.alexpershin.feed.data.model.FeedResponse.FeedDto
import com.alexpershin.feed.domain.model.Feed
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

/**
 * This test class doesn't cover all scenarios, for demo only
 * */
class FeedDtoMapperTest {

    private val sut = FeedDtoMapper()

    @Test
    fun `WHEN category=Payments, THEN assert mapping is correct`() {
        // given
        val dto = FeedDto(
            feedItemUid = "id",
            amount = FeedDto.AmountDto("GBP", 1454),
            direction = "out",
            counterPartyName = "Asda",
            spendingCategory = "PAYMENTS",
            transactionTime = "2024-01-29T00:06:01Z"
        )

        val expected = Feed(
            id = "id",
            amount = 14.54,
            currency = "GBP",
            direction = Feed.Direction.Out,
            merchant = "Asda",
            category = Feed.Category.Payments,
            dateTime = LocalDateTime.of(2024, 1, 29, 0, 6, 1)
        )

        // when
        val result = sut.map(dto)

        // then
        Assertions.assertEquals(
            expected,
            result,
        )
    }

    @Test
    fun `WHEN category=Income, THEN assert mapping is correct`() {
        // given
        val dto = FeedDto(
            feedItemUid = "id",
            amount = FeedDto.AmountDto("GBP", 30000),
            direction = "out",
            counterPartyName = "Asda",
            spendingCategory = "INCOME",
            transactionTime = "2024-01-29T00:06:01Z"
        )

        val expected = Feed(
            id = "id",
            amount = 300.0,
            currency = "GBP",
            direction = Feed.Direction.Out,
            merchant = "Asda",
            category = Feed.Category.Income,
            dateTime = LocalDateTime.of(2024, 1, 29, 0, 6, 1)
        )

        // when
        val result = sut.map(dto)

        // then
        Assertions.assertEquals(
            expected,
            result,
        )
    }

    @Test
    fun `WHEN category=Unknown, THEN assert mapping is correct`() {
        // given
        val dto = FeedDto(
            feedItemUid = "id",
            amount = FeedDto.AmountDto("GBP", 30000),
            direction = "out",
            counterPartyName = "Asda",
            spendingCategory = "random category",
            transactionTime = "2024-01-29T00:06:01Z"
        )

        val expected = Feed(
            id = "id",
            amount = 300.0,
            currency = "GBP",
            direction = Feed.Direction.Out,
            merchant = "Asda",
            category = Feed.Category.Unknown,
            dateTime = LocalDateTime.of(2024, 1, 29, 0, 6, 1)
        )

        // when
        val result = sut.map(dto)

        // then
        Assertions.assertEquals(
            expected,
            result,
        )
    }

    @Test
    fun `WHEN direction=In, THEN assert mapping is correct`() {
        // given
        val dto = FeedDto(
            feedItemUid = "id",
            amount = FeedDto.AmountDto("GBP", 30000),
            direction = "in",
            counterPartyName = "Asda",
            spendingCategory = "INCOME",
            transactionTime = "2024-01-29T00:06:01Z"
        )

        val expected = Feed(
            id = "id",
            amount = 300.0,
            currency = "GBP",
            direction = Feed.Direction.In,
            merchant = "Asda",
            category = Feed.Category.Income,
            dateTime = LocalDateTime.of(2024, 1, 29, 0, 6, 1)
        )

        // when
        val result = sut.map(dto)

        // then
        Assertions.assertEquals(
            expected,
            result,
        )
    }
}