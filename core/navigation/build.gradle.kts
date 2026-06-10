plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.android.compose)
    alias(libs.plugins.movieapp.koin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.app.movieapp.core.navigation"
}

dependencies {
    api(libs.androidx.navigation3.runtime)

    implementation(libs.androidx.navigation3.ui)
    implementation(libs.kotlinx.serialization.json)
}
