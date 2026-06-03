plugins {
    alias(libs.plugins.movieapp.kotlin.library)
}
// Feature DOMAIN layer — pure Kotlin/JVM. Layer-matched access: may use :common:domain and
// :core:contract only. No Android, no Ktor, no Room. (paging-common is a pure-JVM artifact.)

dependencies {
    api(project(":common:domain"))
    implementation(project(":core:contract"))
    implementation(libs.androidx.paging.common)
    implementation(libs.kotlinx.coroutines.core)
    // koin-core is pure Kotlin (no Android) — fine in a pure domain module for use-case wiring.
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}
