package com.alexpershin.feed.domain.model

import java.time.LocalDateTime

class Feed(
    val id: String,
    val amount: Double,
    val currency: String,
    val direction: Direction,
    val merchant: String,
    val category: Category,
    val dateTime: LocalDateTime
) {
    sealed interface Direction {
        object In : Direction
        object Out : Direction
    }

    sealed interface Category {
        object Groceries : Category
        object Travel : Category
        object Health : Category
        object Savings : Category
        object Payments : Category
        object Income : Category
        object Unknown : Category
    }
}
