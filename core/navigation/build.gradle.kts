plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.android.compose)
    alias(libs.plugins.movieapp.koin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.app.movieapp.core.navigation"
}

dependencies {
    // Nav3 runtime gives us NavKey / NavBackStack / EntryProviderBuilder.
    api(libs.androidx.navigation3.runtime)
    // Nav3 UI provides LocalNavAnimatedContentScope, used by the shared-element helper.
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.kotlinx.serialization.json)
}
