package com.alexpershin.savinggoals.domain.usecase

interface CreateSavingGoalUseCase {
    suspend fun execute(): Result<Unit>
}