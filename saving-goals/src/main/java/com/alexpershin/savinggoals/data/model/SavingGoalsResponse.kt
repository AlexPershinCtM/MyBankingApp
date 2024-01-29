package com.alexpershin.savinggoals.data.model

import androidx.annotation.Keep

@Keep
data class SavingGoalsResponse(
    val savingsGoalList: List<SavingsGoalDto>
) {
    data class SavingsGoalDto(
        val name: String,
        val savedPercentage: Int,
        val savingsGoalUid: String,
        val state: String,
        val target: Target,
        val totalSaved: TotalSaved
    ) {

        data class Target(
            val currency: String,
            val minorUnits: Long
        )

        data class TotalSaved(
            val currency: String,
            val minorUnits: Long
        )
    }
}


