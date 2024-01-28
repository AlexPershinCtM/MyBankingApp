package com.alexpershin.core.network.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitFactory @Inject constructor(
    private val gsonFactory: GsonConverterFactory
) {

    fun create(
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit {
        val retrofit =
            Retrofit.Builder()
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build()

        return retrofit
    }


    companion object {
        private const val BASE_URL = "https://readstori.com/api/"
    }
}

inline fun <reified T> Retrofit.createApiService(): T {
    return this.create(T::class.java)
}

