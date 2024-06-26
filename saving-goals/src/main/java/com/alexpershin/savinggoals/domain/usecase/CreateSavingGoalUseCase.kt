package com.alexpershin.savinggoals.domain.usecase

internal interface CreateSavingGoalUseCase {
    suspend fun execute(): Result<Unit>
}