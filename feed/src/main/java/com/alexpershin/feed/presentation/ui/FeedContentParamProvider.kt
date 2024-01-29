package com.alexpershin.feed.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.alexpershin.feed.presentation.model.FeedUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class FeedContentParamProvider : PreviewParameterProvider<ImmutableList<FeedUiModel>> {

    private val items = persistentListOf<FeedUiModel>(
        FeedUiModel(
            id = "",
            icon = Icons.Rounded.Home,
            amount = "33.14",
            currency = FeedUiModel.CurrencySymbol.GBP,
            sign = FeedUiModel.Sign.Minus,
            merchant = "Asda",
            dateTime = "30-01-2024 16:27"
        ),
        FeedUiModel(
            id = "",
            icon = Icons.Rounded.ShoppingCart,
            amount = "104.25",
            currency = FeedUiModel.CurrencySymbol.GBP,
            sign = FeedUiModel.Sign.Minus,
            merchant = "Gucci",
            dateTime = "30-01-2024 16:27"
        ),
        FeedUiModel(
            id = "",
            icon = Icons.Rounded.ShoppingCart,
            amount = "104.25",
            currency = FeedUiModel.CurrencySymbol.GBP,
            sign = FeedUiModel.Sign.Plus,
            merchant = "Salary",
            dateTime = "30-01-2024 16:27"
        )
    )

    override val values = sequenceOf(items)

}