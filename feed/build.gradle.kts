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

        testInstrumentationRunner = "com.alexpershin.base.HiltTestRunner"
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
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(projects.common.ui)
    implementation(projects.common.navigation)
    implementation(projects.core)
    implementation(libs.androidx.runner)

    implementation(libs.kotlinReflect)

    // dagger
    implementation(libs.daggerRuntime)
    implementation(libs.daggerCompiler)
    implementation(libs.daggerHiltRuntime)
    implementation(libs.daggerHiltNavigationCompose)
    kapt(libs.daggerHiltCompiler)

    implementation(libs.lifecycleRuntimeKtx)

    // unit tests
    testImplementation(projects.common.test)

    // android test
    androidTestImplementation(libs.mockkAndroid)
    androidTestImplementation(libs.mockkAndroidAgent)
    androidTestImplementation(libs.kotlinCollections)
    androidTestImplementation(libs.composeTest)
    androidTestImplementation(libs.composeManifest)
    debugImplementation(libs.composeManifest)
    androidTestImplementation(libs.daggerAndroidTest)
    kaptAndroidTest(libs.daggerHiltAndroidCompiler)
    kaptAndroidTest(libs.daggerHiltCompiler)
}

tasks.withType<Test>{
    useJUnitPlatform()
}

kapt {
    correctErrorTypes = true
}