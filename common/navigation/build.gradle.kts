plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.alexpershin.common.navigation"
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
//    api(libs.appcompat)
//    api(libs.material)

    api(platform(libs.composeBom))
    implementation(libs.composeNavigation)

    // dagger
    implementation(libs.daggerRuntime)
    implementation(libs.daggerCompiler)
    implementation(libs.daggerHiltRuntime)
    implementation(libs.daggerHiltNavigationCompose)
    kapt(libs.daggerHiltCompiler)

//    api(libs.composeUi)
//    api(libs.lifecycleRuntimeCompose)
//    api(libs.activityCompose)

    // TODO where the tests should be?
//    debugImplementation(libs.composeTooling)
//    debugImplementation(libs.composeManifest)

//    testImplementation(libs.junit)
}

kapt {
    correctErrorTypes = true
}