package com.alexpershin.savinggoals.data.model

import androidx.annotation.Keep

@Keep
data class AddMoneyBody(
    val amount: Amount,
) {
    data class Amount(
        val currency: String,
        val minorUnits: Long
    )
}
