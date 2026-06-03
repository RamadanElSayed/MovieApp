plugins {
    alias(libs.plugins.movieapp.kotlin.library)
}
// settings DOMAIN — pure Kotlin. Wraps the UserPreferencesRepository contract in use cases.

dependencies {
    api(project(":common:domain"))
    implementation(project(":core:contract"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}
