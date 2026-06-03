plugins {
    alias(libs.plugins.movieapp.android.feature)
}

android {
    namespace = "com.app.movieapp.feature.search.presentation"
}

dependencies {
    implementation(project(":feature:search:domain"))
}
