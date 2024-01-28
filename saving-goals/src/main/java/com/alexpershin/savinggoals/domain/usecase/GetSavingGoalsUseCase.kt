package com.alexpershin.savinggoals.domain.usecase

import com.alexpershin.savinggoals.domain.model.SavingGoal

interface GetSavingGoalsUseCase {
    suspend fun execute(): Result<List<SavingGoal>>
}