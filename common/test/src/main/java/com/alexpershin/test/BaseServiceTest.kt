package com.alexpershin.test

import com.google.gson.GsonBuilder
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseServiceTest<Service> {

    private var _sut: Service? = null

    protected val sut: Service
        get() = requireNotNull(_sut)

    protected lateinit var server: MockWebServer

    abstract val serviceClass: Class<Service>

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()

        _sut = Retrofit.Builder().baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
            .create(serviceClass)
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }
}