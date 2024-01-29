package com.alexpershin.feed.presentation.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.alexpershin.core.extentions.parseToString
import com.alexpershin.feed.domain.model.Feed
import com.alexpershin.feed.presentation.model.FeedUiModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

internal class FeedUiModelMapper @Inject constructor() {

    fun map(model: Feed): FeedUiModel {
        return FeedUiModel(
            id = model.id,
            icon = mapIcon(model.category),
            amount = model.amount.parseToString(),
            currency = mapCurrencySymbol(model.currency),
            sign = mapDirection(model.direction),
            merchant = model.merchant,
            dateTime = mapDateTime(model.dateTime),
        )
    }

    private fun mapCurrencySymbol(targetCurrency: String): FeedUiModel.CurrencySymbol {
        return when (targetCurrency) {
            "GBP" -> FeedUiModel.CurrencySymbol.GBP
            "EUR" -> FeedUiModel.CurrencySymbol.EUR
            "USD" -> FeedUiModel.CurrencySymbol.USD
            else -> FeedUiModel.CurrencySymbol.Unknown
        }
    }


    /**
     * Ideally this logic should be moved to separate class
     * */
    private fun mapDateTime(dateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("DD-MM-yyyy HH:mm")
        return dateTime.format(formatter)
    }

    private fun mapDirection(direction: Feed.Direction): FeedUiModel.Sign {
        return when (direction) {
            Feed.Direction.In -> FeedUiModel.Sign.Plus
            Feed.Direction.Out -> FeedUiModel.Sign.Minus
        }
    }

    private fun mapIcon(category: Feed.Category): ImageVector {
        return when (category) {
            Feed.Category.Health -> Icons.Rounded.Favorite
            Feed.Category.Savings -> Icons.Rounded.Home
            else -> Icons.Rounded.ShoppingCart
        }
    }
}