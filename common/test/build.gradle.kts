plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    namespace = "com.alexpershin.test"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    testApi(libs.junit5Api)
    testApi(libs.junit5Engine)
    testApi(libs.junit5Params)
    testApi(libs.extJUnit)
    testApi(libs.kotlinCoroutinesTest)
    testApi(libs.mockk)
    testApi(libs.turbine)
    testApi(libs.robolectric)
    testApi(libs.mockWebServer)
    testApi(libs.retrofitGsonConverter)
    testApi(libs.retrofitCoroutinesAdapter)
}