package com.alexpershin.savinggoals.data.api

import com.alexpershin.savinggoals.data.model.AddMoneyBody
import com.alexpershin.savinggoals.data.model.AddMoneyToSavingGoalResponse
import com.alexpershin.savinggoals.data.model.CreateSavingGoalBody
import com.alexpershin.savinggoals.data.model.SavingGoalsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface SavingGoalsService {

    @GET("/api/v2/account/{$ACCOUNT_UID_PARAM}/savings-goals")
    fun fetchSavingGoals(
        @Path(ACCOUNT_UID_PARAM) accountUid: String,
    ): Deferred<Response<SavingGoalsResponse>>


    @PUT("/api/v2/account/{$ACCOUNT_UID_PARAM}/savings-goals/{$SAVING_GOAL_UID_PARAM}/add-money/{$TRANSFER_UID_PARAM}")
    fun addMoneyToSavingGoal(
        @Path(ACCOUNT_UID_PARAM) accountUid: String,
        @Path(SAVING_GOAL_UID_PARAM) savingGoalUid: String,
        @Path(TRANSFER_UID_PARAM) transferUid: String,
        @Body requestBody: AddMoneyBody,
    ): Deferred<Response<AddMoneyToSavingGoalResponse>>


    @PUT("/api/v2/account/{$ACCOUNT_UID_PARAM}/savings-goals")
    fun createSavingGoal(
        @Path(ACCOUNT_UID_PARAM) accountUid: String,
        @Body createSavingGoal: CreateSavingGoalBody,
    ): Deferred<Response<Unit>>

    private companion object {
        private const val ACCOUNT_UID_PARAM = "accountUid"
        private const val SAVING_GOAL_UID_PARAM = "savingGoalUid"
        private const val TRANSFER_UID_PARAM = "transferUid"
    }
}