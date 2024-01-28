package com.alexpershin.core.preferences.impl


import com.alexpershin.core.BuildConfig
import com.alexpershin.core.preferences.SecurePreferences
import javax.inject.Inject

/**
 * Simulate extracting values secure preferences.
 *
 * This implementation is for demonstration purpose only, don't use it in production app!
 * */
internal class SecurePreferencesImpl @Inject constructor() : SecurePreferences {
    override val accountUid: String
        get() = BuildConfig.ACCOUNT_UID
    override val accessToken: String
        get() = BuildConfig.ACCESS_TOKEN
    override val categoryUid: String
        get() = BuildConfig.CATEGORY_UID
}