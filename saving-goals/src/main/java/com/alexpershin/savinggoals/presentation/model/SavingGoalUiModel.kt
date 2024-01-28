package com.alexpershin.savinggoals.presentation.model

data class SavingGoalUiModel(
    val id: String,
    val name: String,
    val currencySymbol: CurrencySymbol,
    val saved: String,
    val target: String,
    val currency: String,
) {
    sealed class CurrencySymbol(val value : String) {
        object USD : CurrencySymbol("$")
        object GBP : CurrencySymbol("£")
        object EUR : CurrencySymbol("€")
        object Unknown : CurrencySymbol("")

        override fun toString(): String = this.value
    }
}
