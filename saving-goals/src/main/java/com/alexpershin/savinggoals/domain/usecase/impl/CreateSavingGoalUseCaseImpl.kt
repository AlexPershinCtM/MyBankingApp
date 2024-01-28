package com.alexpershin.savinggoals.domain.usecase.impl

import com.alexpershin.core.preferences.SecurePreferences
import com.alexpershin.savinggoals.domain.repository.SavingGoalsRepository
import com.alexpershin.savinggoals.domain.usecase.CreateSavingGoalUseCase
import javax.inject.Inject

internal class CreateSavingGoalUseCaseImpl @Inject constructor(
    private val savingGoalsRepository: SavingGoalsRepository,
    private val securePreferences: SecurePreferences,
) : CreateSavingGoalUseCase {
    override suspend fun execute(): Result<Unit> {
        val accountUid = securePreferences.accountUid
        val (goalTarget, goalName) = generateRandomGoal()

        return savingGoalsRepository.createSavingGoal(
            accountUid = accountUid,
            goalName = goalName,
            goalTarget = goalTarget
        )
    }

    /**
    * Ideally should be moved to separate class
    *  */
    private fun generateRandomGoal(): Pair<Double, String> {
        val randomGoal = listOf(
            5000 to "Vacation to Europe",
            10000 to "Down payment on a new car",
            2500 to "Emergency medical expenses",
            15000 to "Home renovation project",
            3000 to "New laptop and accessories",
            7500 to "Family trip to Disney World",
            20000 to "Deposit on a house",
            1500 to "Professional certification course",
            12000 to "Wedding",
            4000 to "Home theater system",
            8000 to "Cross-country road trip",
            6500 to "Down payment on a motorcycle",
            18000 to "Graduate degree program",
            2000 to "New wardrobe",
            9000 to "Backyard landscaping project",
            25000 to "Round-the-world trip",
            3500 to "Kitchen appliance upgrade",
            11000 to "Down payment on an investment property",
            5500 to "Luxury watch",
            30000 to "Early retirement investments",
        ).random()

        return Pair(randomGoal.first.toDouble(), randomGoal.second)
    }

}

