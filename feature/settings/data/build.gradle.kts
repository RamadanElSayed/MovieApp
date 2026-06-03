plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.koin)
}

android {
    namespace = "com.app.movieapp.feature.settings.data"
}

dependencies {
    implementation(project(":feature:settings:domain"))
    implementation(project(":core:contract"))

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.core)
}
