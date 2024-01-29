plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.alexpershin.mystarlingapp"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.alexpershin.mystarlingapp"
        minSdk = libs.versions.minSdk.get().toInt()

        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = libs.versions.testInstrumentationRunner.get()
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
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

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.common.ui)
    implementation(projects.common.navigation)
    implementation(projects.feed)
    implementation(projects.savingGoals)

    implementation(libs.composeNavigation)

    // dagger
    implementation(libs.daggerRuntime)
    implementation(libs.daggerCompiler)
    implementation(libs.daggerHiltRuntime)
    implementation(libs.daggerHiltNavigationCompose)
    kapt(libs.daggerHiltCompiler)

    // unit tests
    testImplementation(projects.common.test)
    testImplementation(libs.composeTest)
    testImplementation(libs.composeManifest)
    testImplementation(libs.kotlinCollections)
    testImplementation(projects.core)
}

kapt {
    correctErrorTypes = true
}