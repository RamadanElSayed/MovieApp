plugins {
    alias(libs.plugins.movieapp.android.feature)
}

android {
    namespace = "com.app.movieapp.feature.settings.presentation"
}

dependencies {
    implementation(project(":feature:settings:domain"))
    // AppCompat provides the per-app locale API (AppCompatDelegate.setApplicationLocales).
    implementation(libs.androidx.appcompat)
}
