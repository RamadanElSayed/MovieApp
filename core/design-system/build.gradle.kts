plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.android.compose)
}

android {
    namespace = "com.app.movieapp.core.designsystem"
}

dependencies {
    implementation(project(":core:contract"))
    implementation(libs.androidx.compose.material3.adaptive)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
}
