package com.alexpershin.savinggoals.domain.repository

import com.alexpershin.savinggoals.domain.model.SavingGoal

interface SavingGoalsRepository {

    suspend fun addMoneyToSavingGoal(
        accountUid: String,
        savingGoalUid: String,
        transferUid: String,
        currency: String,
        moneyToAdd: Long,
    ): Result<Unit>

    suspend fun fetchSavingGoals(accountUid: String): Result<List<SavingGoal>>
    suspend fun createSavingGoal(
        accountUid: String,
        goalName: String,
        goalTarget: Double
    ): Result<Unit>
}