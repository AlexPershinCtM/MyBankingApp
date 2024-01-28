package com.alexpershin.savinggoals.data.model

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