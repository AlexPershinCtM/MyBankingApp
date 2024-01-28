package com.alexpershin.savinggoals.domain.usecase

interface AddMoneySavingGoalUseCase {
    suspend fun execute(id: String, currency: String, moneyToAdd: Double): Result<Unit>
}