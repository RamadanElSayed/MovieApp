plugins {
    alias(libs.plugins.movieapp.android.feature)
}

android {
    namespace = "com.app.movieapp.feature.movieslist.presentation"
}

dependencies {
    implementation(project(":feature:movies-list:domain"))
}
