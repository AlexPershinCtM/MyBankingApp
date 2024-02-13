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
    api(libs.junit5Api)
    api(libs.junit5Engine)
    api(libs.junit5Params)
    api(libs.extJUnit)
    api(libs.kotlinCoroutinesTest)
    api(libs.mockk)
    api(libs.turbine)
    api(libs.robolectric)
    api(libs.mockWebServer)
    api(libs.retrofitGsonConverter)
    api(libs.retrofitCoroutinesAdapter)
}