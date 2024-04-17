package com.alexpershin.feed.presentation.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import com.alexpershin.feed.domain.model.Feed
import com.alexpershin.feed.presentation.model.FeedUiModel
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class FeedUiModelMapperTest {

    private val sut = FeedUiModelMapper()

    @Test
    fun `WHEN operation=In, THENmap Feed model to FeedUiModel`() {
        // given
        val feedMode = Feed(
            id = "3",
            amountMinors = 1425,
            currency = "GBP",
            direction = Feed.Direction.In,
            merchant = "Asda",
            category = Feed.Category.Groceries,
            dateTime = LocalDateTime.of(2024, 1, 29, 0, 6, 1)
        )

        val expected = FeedUiModel(
            id = "3",
            icon = Icons.Rounded.ShoppingCart,
            amount = "14.25",
            currency = FeedUiModel.CurrencySymbol.GBP,
            sign = FeedUiModel.Sign.Plus,
            merchant = "Asda",
            dateTime = "29-01-2024 00:06"
        )
        // when
        val result = sut.map(feedMode)

        // then
        assertEquals(
            result,
            expected
        )
    }

    @Test
    fun `WHEN operation=Out, THENmap Feed model to FeedUiModel`() {
        // given
        val feedModel = Feed(
            id = "3",
            amountMinors = 1425,
            currency = "GBP",
            direction = Feed.Direction.Out,
            merchant = "Asda",
            category = Feed.Category.Groceries,
            dateTime = LocalDateTime.of(2024, 1, 29, 0, 6, 1)
        )

        val expected = FeedUiModel(
            id = "3",
            icon = Icons.Rounded.ShoppingCart,
            amount = "14.25",
            currency = FeedUiModel.CurrencySymbol.GBP,
            sign = FeedUiModel.Sign.Minus,
            merchant = "Asda",
            dateTime = "29-01-2024 00:06"
        )
        // when
        val result = sut.map(feedModel)

        // then
        assertEquals(
            result,
            expected
        )
    }
}
