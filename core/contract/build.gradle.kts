plugins {
    alias(libs.plugins.movieapp.kotlin.library)
}

dependencies {
    api(project(":common:domain"))

    api(libs.kotlinx.coroutines.core)
}
