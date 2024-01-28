package com.alexpershin.core.network.retrofit


import com.alexpershin.core.preferences.SecurePreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val securePreferences: SecurePreferences
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = getTokenFromSecurePreferences()
        val builder = chain.request().newBuilder()

        if (token.isNotEmpty()) {
            builder.addHeader(KEY_AUTHORIZATION, "Bearer $token")
        }

        return chain.proceed(builder.build())
    }

    /**
     * Simulate extracting access token from secure preferences
     * */
    private fun getTokenFromSecurePreferences(): String {
        return securePreferences.accessToken
    }

    companion object {
        private const val KEY_AUTHORIZATION = "Authorization"
    }
}