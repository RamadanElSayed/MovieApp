plugins {
    alias(libs.plugins.movieapp.kotlin.library)
}

dependencies {
    api(project(":common:domain"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}
