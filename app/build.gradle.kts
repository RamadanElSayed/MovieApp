plugins {
    alias(libs.plugins.movieapp.android.application)
    alias(libs.plugins.movieapp.android.compose)
    alias(libs.plugins.movieapp.koin)
}

android {
    namespace = "com.app.movieapp"

    defaultConfig {
        applicationId = "com.app.movieapp"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    // ---- Core ----
    implementation(project(":core:design-system"))
    implementation(project(":core:navigation"))
    implementation(project(":core:contract"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))

    // ---- Common (shared modules + AndroidResourceProvider) ----
    implementation(project(":common:data"))
    implementation(project(":common:presentation"))

    // ---- Features: presentation + data layers wired here (the composition root) ----
    implementation(project(":feature:movies-list:domain"))
    implementation(project(":feature:movies-list:presentation"))
    implementation(project(":feature:movies-list:data"))
    implementation(project(":feature:movie-details:domain"))
    implementation(project(":feature:movie-details:presentation"))
    implementation(project(":feature:search:domain"))
    implementation(project(":feature:search:presentation"))
    implementation(project(":feature:search:data"))
    implementation(project(":feature:favorites:domain"))
    implementation(project(":feature:favorites:presentation"))
    implementation(project(":feature:favorites:data"))
    implementation(project(":feature:settings:domain"))
    implementation(project(":feature:settings:presentation"))
    implementation(project(":feature:settings:data"))

    // ---- Platform / UI ----
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // ---- Navigation 3 ----
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    // ---- DI ----
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.workmanager)

    // ---- Images ----
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // ---- Background sync ----
    implementation(libs.androidx.work.runtime.ktx)
}
