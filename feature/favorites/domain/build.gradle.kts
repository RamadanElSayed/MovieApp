plugins {
    alias(libs.plugins.movieapp.kotlin.library)
}
// favorites DOMAIN — pure Kotlin. Consumes the cross-feature MovieProvider contract (to resolve
// favourited ids into movie data) and declares its own FavoritesRepository contract.

dependencies {
    api(project(":common:domain"))
    implementation(project(":core:contract"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}
