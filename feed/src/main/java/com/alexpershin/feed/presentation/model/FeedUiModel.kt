package com.alexpershin.feed.presentation.model

import androidx.compose.ui.graphics.vector.ImageVector

data class FeedUiModel(
    val id: String,
    val icon: ImageVector,
    val amount: String,
    val currency: CurrencySymbol,
    val sign: Sign,
    val merchant: String,
    val dateTime: String
) {
    sealed class Sign(val value: String) {
        object Plus : Sign("+")
        object Minus : Sign("-")
    }

    sealed class CurrencySymbol(val value : String) {
        object USD : CurrencySymbol("$")
        object GBP : CurrencySymbol("£")
        object EUR : CurrencySymbol("€")
        object Unknown : CurrencySymbol("")

        override fun toString(): String = this.value
    }

}