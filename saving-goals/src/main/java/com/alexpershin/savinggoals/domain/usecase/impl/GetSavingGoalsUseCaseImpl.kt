package com.alexpershin.savinggoals.domain.usecase.impl

import com.alexpershin.core.preferences.SecurePreferences
import com.alexpershin.savinggoals.domain.model.SavingGoal
import com.alexpershin.savinggoals.domain.repository.SavingGoalsRepository
import com.alexpershin.savinggoals.domain.usecase.GetSavingGoalsUseCase
import javax.inject.Inject

internal class GetSavingGoalsUseCaseImpl @Inject constructor(
    private val repository: SavingGoalsRepository,
    private val securePreferences: SecurePreferences

) : GetSavingGoalsUseCase {
    override suspend fun execute(): Result<List<SavingGoal>> {
        val accountUid = securePreferences.accountUid

        return repository.fetchSavingGoals(accountUid)
    }
}