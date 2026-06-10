plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.android.room)
    alias(libs.plugins.movieapp.koin)
}

android {
    namespace = "com.app.movieapp.core.database"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.paging.common)

    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.junit)
}
