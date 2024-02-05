package com.alexpershin.feed.data.di

import com.alexpershin.core.network.retrofit.HeaderInterceptor
import com.alexpershin.core.network.retrofit.RetrofitFactory
import com.alexpershin.feed.data.api.FeedService
import com.alexpershin.feed.data.repository.FeedRepositoryImpl
import com.alexpershin.feed.domain.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FeedModule {

    @Provides
    @Singleton
    fun provideFeedService(
        okHttpClient: OkHttpClient,
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor,
        retrofitFactory: RetrofitFactory,
    ): FeedService {

        val baseUrl = "https://api-sandbox.starlingbank.com"

        return retrofitFactory.create(
            okHttpClient = okHttpClient
                .newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build(),
            baseUrl = baseUrl
        )
            .create(FeedService::class.java)
    }

    @Provides
    @Singleton
    internal fun providesFeedRepository(impl: FeedRepositoryImpl): FeedRepository = impl

}
