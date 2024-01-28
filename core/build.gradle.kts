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

//    hilt {
//        enableAggregatingTask = true
//    }
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    // dagger
    implementation(libs.daggerRuntime)
    implementation(libs.daggerCompiler)
    implementation(libs.daggerHiltRuntime)
    implementation(libs.daggerHiltNavigationCompose)
    kapt(libs.daggerHiltCompiler)

    val retrofitVersion = "2.7.0"
    val retrofitCoroutinesAdapterVersion = "0.9.2"
    val okHttpVersion = "4.2.2"
    val gsonVersion = "2.8.6"

    //Retrofit 2
    api("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$retrofitCoroutinesAdapterVersion")

    //OkHttp client
    api("com.squareup.okhttp3:okhttp:$okHttpVersion")
    api("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

    //Gson
    api("com.google.code.gson:gson:$gsonVersion")

    implementation(libs.core.ktx) // TODO remove unused dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

kapt {
    correctErrorTypes = true
}