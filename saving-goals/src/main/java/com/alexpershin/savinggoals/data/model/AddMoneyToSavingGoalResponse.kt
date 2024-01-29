package com.alexpershin.savinggoals.data.model

import androidx.annotation.Keep

@Keep
data class AddMoneyToSavingGoalResponse(
    val success: Boolean,
    val transferUid: String
)

@Keep
data class AddMoneyToSavingGoalErrorResponse(
    val errors: List<Error>,
    val success: Boolean
) {
    data class Error(
        val message: String
    )
}

