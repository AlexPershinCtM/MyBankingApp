
plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
}


android {
    namespace = "com.alexpershin.feed"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = libs.versions.testInstrumentationRunner.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    useLibrary("android.test.mock")

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
}

dependencies {

    implementation(projects.common.ui)
    implementation(projects.common.navigation)
    implementation(projects.core)

    // dagger
    implementation(libs.daggerRuntime)
    implementation(libs.daggerCompiler)
    implementation(libs.daggerHiltRuntime)
    implementation(libs.daggerHiltNavigationCompose)
    kapt(libs.daggerHiltCompiler)

    implementation(libs.core.ktx)

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    testImplementation(libs.junit5Api)
    testImplementation(libs.junit5Engine)
    testImplementation(libs.junit5Params)
    testImplementation(libs.extJUnit)
    testImplementation(libs.kotlinCoroutinesTest)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
}

tasks.withType<Test>{
    useJUnitPlatform()
}

kapt {
    correctErrorTypes = true
}