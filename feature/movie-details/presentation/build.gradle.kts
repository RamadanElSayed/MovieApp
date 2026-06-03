plugins {
    alias(libs.plugins.movieapp.android.feature)
}

android {
    namespace = "com.app.movieapp.feature.moviedetails.presentation"
}

dependencies {
    implementation(project(":feature:movie-details:domain"))
}
