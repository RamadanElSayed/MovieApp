plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.koin)
}

android {
    namespace = "com.app.movieapp.feature.favorites.data"
}

dependencies {
    implementation(project(":feature:favorites:domain"))
    implementation(project(":common:data"))
    implementation(project(":core:database"))
    implementation(project(":core:contract"))
    implementation(libs.kotlinx.coroutines.core)
}
