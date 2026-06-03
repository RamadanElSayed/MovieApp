plugins {
    alias(libs.plugins.movieapp.android.feature)
}

android {
    namespace = "com.app.movieapp.feature.favorites.presentation"
}

dependencies {
    implementation(project(":feature:favorites:domain"))
}
