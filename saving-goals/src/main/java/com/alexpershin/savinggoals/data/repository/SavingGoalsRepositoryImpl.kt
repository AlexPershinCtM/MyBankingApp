package com.alexpershin.savinggoals.data.repository

import com.alexpershin.core.network.StarlingException
import com.alexpershin.savinggoals.data.api.SavingGoalsService
import com.alexpershin.savinggoals.data.mapper.SavingGoalsDtoMapper
import com.alexpershin.savinggoals.data.model.AddMoneyBody
import com.alexpershin.savinggoals.data.model.AddMoneyToSavingGoalErrorResponse
import com.alexpershin.savinggoals.data.model.CreateSavingGoalBody
import com.alexpershin.savinggoals.domain.model.SavingGoal
import com.alexpershin.savinggoals.domain.repository.SavingGoalsRepository
import com.google.gson.Gson
import okhttp3.ResponseBody
import javax.inject.Inject

internal class SavingGoalsRepositoryImpl @Inject constructor(
    private val savingGoalsService: SavingGoalsService,
    private val savingGoalsDtoMapper: SavingGoalsDtoMapper,
) : SavingGoalsRepository {

    override suspend fun createSavingGoal(
        accountUid: String,
        goalName: String,
        goalTarget: Double
    ): Result<Unit> {
        return try {
            val body = CreateSavingGoalBody(
                currency = "GBP",
                name = goalName,
                target = CreateSavingGoalBody.Target(
                    currency = "GBP",
                    minorUnits = (goalTarget * 100).toLong()
                )
            )

            val result = savingGoalsService.createSavingGoal(accountUid, body).await()

            if (result.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(StarlingException.HttpException(result.code()))
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }

    }

    override suspend fun addMoneyToSavingGoal(
        accountUid: String,
        savingGoalUid: String,
        transferUid: String,
        currency: String,
        moneyToAdd: Long,
    ): Result<Unit> {
        return try {
            val requestBody = AddMoneyBody(
                amount = AddMoneyBody.Amount(
                    currency = currency,
                    minorUnits = moneyToAdd
                ),
            )

            val result = savingGoalsService.addMoneyToSavingGoal(
                accountUid = accountUid,
                savingGoalUid = savingGoalUid,
                transferUid = transferUid,
                requestBody = requestBody
            ).await()

            if (result.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = result.errorBody()

                return if (errorBody == null) {
                    Result.failure(StarlingException.HttpException(result.code()))
                } else {
                    mapAddMoneyToSavingGoalError(errorBody, result.code())
                }
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    private fun mapAddMoneyToSavingGoalError(errorBody: ResponseBody, code: Int): Result<Unit> {
        val errorResponse = parseErrorResponse<AddMoneyToSavingGoalErrorResponse>(errorBody)

        return if (errorResponse.errors.firstOrNull()?.message == "INSUFFICIENT_FUNDS") {
            Result.failure(StarlingException.InsufficientFunds)
        } else {
            Result.failure(StarlingException.HttpException(code))
        }
    }

    override suspend fun fetchSavingGoals(accountUid: String): Result<List<SavingGoal>> {
        return try {
            val result = savingGoalsService.fetchSavingGoals(accountUid).await()

            if (result.isSuccessful) {
                val items = requireNotNull(result.body()).savingsGoalList
                Result.success(
                    items.map { savingGoalsDtoMapper.map(it) }
                )
            } else {
                Result.failure(StarlingException.HttpException(result.code()))
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    private inline fun <reified T> parseErrorResponse(errorBody: ResponseBody): T {
        val gson = Gson()
        val errorString = errorBody.string()

        return gson.fromJson(errorString, T::class.java)
    }
}