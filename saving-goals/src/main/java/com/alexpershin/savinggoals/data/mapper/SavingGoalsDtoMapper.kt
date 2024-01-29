package com.alexpershin.savinggoals.data.mapper

import com.alexpershin.core.mapper.BaseModelMapper
import com.alexpershin.savinggoals.data.model.SavingGoalsResponse.SavingsGoalDto
import com.alexpershin.savinggoals.domain.model.SavingGoal
import javax.inject.Inject

internal class SavingGoalsDtoMapper @Inject constructor() : BaseModelMapper<SavingsGoalDto, SavingGoal> {

    override fun map(dto: SavingsGoalDto): SavingGoal {
        return SavingGoal(
            id = dto.savingsGoalUid,
            name = dto.name,
            savedAmount = dto.totalSaved.minorUnits.toDouble() / 100f,
            savedCurrency = dto.totalSaved.currency,
            targetAmount = dto.target.minorUnits.toDouble() / 100f,
            targetCurrency = dto.target.currency,

            )
    }
}