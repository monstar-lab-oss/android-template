@file:Suppress("UnstableApiUsage")
// TODO: Remove once https://youtrack.jetbrains.com/issue/KTIJ-19369 is fixed
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("dk.nstack.translation.plugin")
    alias(libs.plugins.convention.android.application)
}

val nStackKey = "LqWLm621BwIxNRzdrei88pKhIIEI2EE8ni8r"
val nStackAppId = "IXmpT4N7MJbGEXvDfGqGH4UKHrmV0EOqFeK0"

translation {
    appId = nStackAppId
    apiKey = nStackKey
    acceptHeader = "en-GB"
}

android {
    namespace = "com.monstarlab"
    flavorDimensions += "default"
    defaultConfig {
        manifestPlaceholders += mapOf("appId" to nStackAppId, "apiKey" to nStackKey)
        applicationId = "com.monstarlab"
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    productFlavors {
        create("dev") {
            manifestPlaceholders += mapOf("APP_NAME" to "MonstarlabDev", "env" to "staging")
            dimension = "default"
            applicationIdSuffix = ".dev"
            buildConfigField("String", "API_URL", "\"https://reqres.in/api/\"")
        }
        create("staging") {
            manifestPlaceholders += mapOf("APP_NAME" to "MonstarlabStaging", "env" to "staging")
            dimension = "default"
            applicationIdSuffix = ".staging"
            buildConfigField("String", "API_URL", "\"https://reqres.in/api/\"")
        }
        create("production") {
            manifestPlaceholders += mapOf("APP_NAME" to "Monstarlab", "env" to "production")
            dimension = "default"
            applicationIdSuffix = ".staging"
            //signingConfig signingConfigs.production
            buildConfigField("String", "API_URL", "\"https://reqres.in/api/\"")
        }
    }


    packaging {
        resources.excludes.add("META-INF/versions/9/previous-compilation-data.bin")
    }
}

kotlin {
    jvmToolchain(17)
}

configurations {
    create("devDebugImplementation")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":designsystem"))

    // Kotlin
    implementation(libs.bundles.kotlin)
    implementation(libs.kotlin.serialization.json)

    // Android
    implementation(libs.bundles.android.core)
    implementation(libs.android.splash)

    implementation(libs.bundles.android.lifecycle)

    implementation(libs.android.navigation.fragment)
    implementation(libs.android.navigation.ui)

    implementation(libs.android.lifecycle.runtime.compose)
    implementation(libs.android.datastore.preferences)

    // Compose
    implementation(platform(libs.android.compose.bom))
    implementation(libs.bundles.android.compose.core)
    implementation(libs.android.compose.material)
    implementation(libs.android.compose.material.windowsize)
    implementation(libs.android.activity.compose)
    implementation(libs.android.lifecycle.viewmodel.compose)
    implementation(libs.bundles.google.accompanist)
    implementation (libs.android.compose.ui.tooling.preview)
    debugImplementation(libs.android.compose.ui.tooling)

    // Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Networking
    implementation(libs.retrofit.converter)
    implementation(libs.retrofit)
    implementation(libs.okhttp.logger)

    implementation(libs.nstack)
    implementation(libs.timber)
    "devDebugImplementation"(libs.leakcanary)
    debugImplementation(libs.chucker.op)
    releaseImplementation(libs.chucker.noop)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)
}
