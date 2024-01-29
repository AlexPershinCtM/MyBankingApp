package com.alexpershin.core.extentions

import java.math.BigDecimal
import java.math.RoundingMode


fun Double.parseToString(): String {
    return try {
        String.format("%.2f", this)
    } catch (ex: NumberFormatException) {
        "0.0"
    }
}

fun Double.setScale(decimal: Int): BigDecimal {
    return BigDecimal(this).setScale(decimal, RoundingMode.HALF_EVEN)
}
