plugins {
    alias(libs.plugins.movieapp.kotlin.library)
}
// movie-details DOMAIN — pure Kotlin. Has NO data layer: it consumes the cross-feature MovieProvider
// contract (implemented by movies-list) for its data. Pure dependency inversion.

dependencies {
    api(project(":common:domain"))
    implementation(project(":core:contract"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}
