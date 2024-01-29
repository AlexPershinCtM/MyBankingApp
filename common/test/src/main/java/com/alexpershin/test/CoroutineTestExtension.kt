package com.alexpershin.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTestExtension(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : BeforeTestExecutionCallback, AfterTestExecutionCallback {

    override fun beforeTestExecution(p0: ExtensionContext?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun afterTestExecution(p0: ExtensionContext?) {
        Dispatchers.resetMain()
    }
}
