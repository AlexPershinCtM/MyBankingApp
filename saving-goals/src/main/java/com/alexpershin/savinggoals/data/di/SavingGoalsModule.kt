package com.alexpershin.savinggoals.data.di

import com.alexpershin.core.network.retrofit.HeaderInterceptor
import com.alexpershin.core.network.retrofit.RetrofitFactory
import com.alexpershin.savinggoals.data.api.SavingGoalsService
import com.alexpershin.savinggoals.data.repository.SavingGoalsRepositoryImpl
import com.alexpershin.savinggoals.domain.repository.SavingGoalsRepository
import com.alexpershin.savinggoals.domain.usecase.AddMoneySavingGoalUseCase
import com.alexpershin.savinggoals.domain.usecase.CreateSavingGoalUseCase
import com.alexpershin.savinggoals.domain.usecase.GetSavingGoalsUseCase
import com.alexpershin.savinggoals.domain.usecase.impl.AddMoneySavingGoalUseCaseImpl
import com.alexpershin.savinggoals.domain.usecase.impl.CreateSavingGoalUseCaseImpl
import com.alexpershin.savinggoals.domain.usecase.impl.GetSavingGoalsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module(includes = [SavingGoalsModule.Internal::class])
@InstallIn(SingletonComponent::class)
class SavingGoalsModule {

    @Provides
    @Singleton
    fun provideSavingGoalsService(
        okHttpClient: OkHttpClient,
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor,
        retrofitFactory: RetrofitFactory,
    ): SavingGoalsService {

        val baseUrl = "https://api-sandbox.starlingbank.com"

        return retrofitFactory.create(
            okHttpClient = okHttpClient
                .newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build(),
            baseUrl = baseUrl
        )
            .create(SavingGoalsService::class.java)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    internal interface Internal {
        @Binds
        fun bindSavingGoalsRepository(impl: SavingGoalsRepositoryImpl): SavingGoalsRepository

        @Binds
        fun bindGetSavingGoalsUseCase(impl: GetSavingGoalsUseCaseImpl): GetSavingGoalsUseCase

        @Binds
        fun bindAddMoneySavingGoalUseCase(impl: AddMoneySavingGoalUseCaseImpl): AddMoneySavingGoalUseCase

        @Binds
        fun bindCreateSavingGoalUseCase(impl: CreateSavingGoalUseCaseImpl): CreateSavingGoalUseCase
    }
}