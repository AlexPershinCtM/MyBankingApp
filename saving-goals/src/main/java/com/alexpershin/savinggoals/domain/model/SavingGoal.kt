package com.alexpershin.savinggoals.domain.model

data class SavingGoal(
    val id: String,
    val name: String,
    val savedAmount: Double,
    val savedCurrency: String,
    val targetAmount: Double,
    val targetCurrency: String,
)