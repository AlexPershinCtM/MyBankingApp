package com.alexpershin.core.logging

interface ErrorLogger {
    fun e(tag: String, error: String)
    fun i(tag: String, error: String)
}