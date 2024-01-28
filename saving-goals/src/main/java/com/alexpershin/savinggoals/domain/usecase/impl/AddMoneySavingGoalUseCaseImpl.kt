package com.alexpershin.savinggoals.domain.usecase.impl

import com.alexpershin.core.preferences.SecurePreferences
import com.alexpershin.savinggoals.domain.repository.SavingGoalsRepository
import com.alexpershin.savinggoals.domain.usecase.AddMoneySavingGoalUseCase
import java.util.UUID
import javax.inject.Inject

internal class AddMoneySavingGoalUseCaseImpl @Inject constructor(
    private val repository: SavingGoalsRepository,
    private val securePreferences: SecurePreferences
) : AddMoneySavingGoalUseCase {
    override suspend fun execute(id: String, currency: String, moneyToAdd: Double): Result<Unit> {
        val accountUid = securePreferences.accountUid
        val transferUid = generateTransferUid()

        return repository.addMoneyToSavingGoal(
            accountUid = accountUid,
            savingGoalUid = id,
            transferUid = transferUid,
            currency = currency,
            moneyToAdd = moneyToAdd
        )
    }

    /**
     * Ideally this logic should be moved to separate class
     * */
    private fun generateTransferUid(): String {
        return UUID.randomUUID().toString()
    }
}