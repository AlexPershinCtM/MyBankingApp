package com.alexpershin.savinggoals.domain.usecase

internal interface AddMoneySavingGoalUseCase {
    suspend fun execute(id: String, currency: String, moneyToAdd: Long): Result<Unit>
}