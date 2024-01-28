package com.alexpershin.savinggoals.data.model

data class AddMoneyBody(
    val amount: Amount,
) {
    data class Amount(
        val currency: String,
        val minorUnits: Long
    )
}
