plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
}

android {
    namespace = "com.alexpershin.common.ui"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()


        testInstrumentationRunner = libs.versions.testInstrumentationRunner.get()
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
}

dependencies {
    api(libs.core.ktx)
    api(libs.appcompat)
    api(libs.material)

    api(platform(libs.composeBom))
    api(libs.composeUi)
    api(libs.lifecycleRuntimeCompose)
    api(libs.activityCompose)
    api(libs.composeGraphics)
    api(libs.composeToolingPreview)
    api(libs.composeMaterial)
    api(libs.composeSwipeToRefresh)

    debugApi(libs.composeTooling)
    debugApi(libs.composeManifest)
}