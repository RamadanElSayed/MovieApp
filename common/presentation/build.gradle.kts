plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.android.compose)
    alias(libs.plugins.movieapp.koin)
}

android {
    namespace = "com.app.movieapp.common.presentation"
}

dependencies {
    // Layer-matched: common-presentation may use core:design-system and common:domain.
    api(project(":common:domain"))
    implementation(project(":core:design-system"))

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.coroutines.core)
}
