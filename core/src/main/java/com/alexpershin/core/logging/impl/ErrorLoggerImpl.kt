package com.alexpershin.core.logging.impl

import android.util.Log
import com.alexpershin.core.logging.ErrorLogger
import javax.inject.Inject

internal class ErrorLoggerImpl @Inject constructor() : ErrorLogger {
    override fun e(tag: String, error: String) {
        Log.e(tag, error)
    }
    override fun i(tag: String, error: String) {
        Log.i(tag, error)
    }
}