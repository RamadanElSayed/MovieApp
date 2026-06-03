plugins {
    alias(libs.plugins.movieapp.kotlin.library)
}
// search DOMAIN — pure Kotlin.

dependencies {
    api(project(":common:domain"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}
