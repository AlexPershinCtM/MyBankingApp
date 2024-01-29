package com.alexpershin.savinggoals.data.model

import androidx.annotation.Keep

@Keep
data class CreateSavingGoalBody(
    val currency: String,
    val name: String,
    val target: Target
) {
    data class Target(
        val currency: String,
        val minorUnits: Long
    )
}