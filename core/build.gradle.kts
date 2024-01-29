import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.alexpershin.core"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        val localPropertiesFile = project.rootProject.file("local.properties")
        val localProperties = Properties()
        if (localPropertiesFile.exists()){
            localProperties.load(localPropertiesFile.inputStream())
        }

        buildConfigField("String", "ACCESS_TOKEN", localProperties.getProperty("accessToken"))
        buildConfigField("String", "ACCOUNT_UID", localProperties.getProperty("accountUid"))
        buildConfigField("String", "CATEGORY_UID", localProperties.getProperty("defaultCategory"))
    }

    buildFeatures {
        buildConfig = true
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
}

dependencies {
    // dagger
    implementation(libs.daggerRuntime)
    implementation(libs.daggerCompiler)
    implementation(libs.daggerHiltRuntime)
    implementation(libs.daggerHiltNavigationCompose)
    kapt(libs.daggerHiltCompiler)

    //Retrofit 2
    api(libs.retrofit)
    implementation(libs.retrofitGsonConverter)
    implementation(libs.retrofitCoroutinesAdapter)

    api(libs.okhttp)
    api(libs.okhttpLogging)
    api(libs.gson)
    api(libs.kotlinCollections)
}

kapt {
    correctErrorTypes = true
}