package com.alexpershin.feed.domain.usecase


interface RoundUpUseCase {
    suspend fun execute(transactions: List<Double>): Double
}