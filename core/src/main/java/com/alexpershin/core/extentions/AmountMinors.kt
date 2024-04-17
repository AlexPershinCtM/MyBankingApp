package com.alexpershin.core.extentions

/**
* Class which represents amount in minors
* e.g  12.34 -> 1234
* e.g  1 -> 100
* e.g  0.01 -> 1
 *
* */
typealias AmountMinors = Long

fun AmountMinors.stringRepresentation() : String {
    val fraction = this % 100
    val number = this / 100

    return "%d.%02d".format(number, fraction)
}