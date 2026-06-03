plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.android.compose)
}

android {
    namespace = "com.app.movieapp.core.designsystem"
}

dependencies {
    // Needs only the shared ThemeMode/DynamicColor enums from the contract (pure models).
    implementation(project(":core:contract"))
    implementation(libs.androidx.compose.material3.adaptive)
    // Shared image-loading for reusable poster components (PosterCard, backdrops).
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
}
