package com.alexpershin.savinggoals.data.model

data class AddMoneyToSavingGoalResponse(
    val success: Boolean,
    val transferUid: String
)


data class AddMoneyToSavingGoalErrorResponse(
    val errors: List<Error>,
    val success: Boolean
) {
    data class Error(
        val message: String
    )
}

