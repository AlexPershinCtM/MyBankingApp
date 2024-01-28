package com.alexpershin.core.extentions


fun Double.parseToString(): String {
    return try {
        String.format("%.2f", this)
    } catch (ex: NumberFormatException) {
        "0.0"
    }
}