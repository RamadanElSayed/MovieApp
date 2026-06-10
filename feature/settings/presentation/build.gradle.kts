plugins {
    alias(libs.plugins.movieapp.android.feature)
}

android {
    namespace = "com.app.movieapp.feature.settings.presentation"
}

dependencies {
    implementation(project(":feature:settings:domain"))

    implementation(libs.androidx.appcompat)
}
