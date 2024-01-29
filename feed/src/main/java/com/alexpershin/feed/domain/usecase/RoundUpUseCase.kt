package com.alexpershin.feed.domain.usecase


internal interface RoundUpUseCase {
    suspend fun execute(transactions: List<Double>): Double
}