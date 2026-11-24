import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

// Lê a chave da API do local.properties (arquivo que não vai para o GitHub)
val localProperties = Properties()
val arquivoLocal = rootProject.file("local.properties")
if (arquivoLocal.exists()) {
    arquivoLocal.inputStream().use { localProperties.load(it) }
}

android {
    namespace = "com.example.n2"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.n2"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "REST_COUNTRIES_KEY",
            "\"" + localProperties.getProperty("REST_COUNTRIES_KEY", "") + "\""
        )
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}