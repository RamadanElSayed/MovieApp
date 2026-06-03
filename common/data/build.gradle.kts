plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.koin)
}

android {
    namespace = "com.app.movieapp.common.data"
}

dependencies {
    // Layer-matched: common-data may use core:network, core:database and common:domain.
    api(project(":common:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.paging.common)
    // Ktor exception types are mapped to AppError here.
    implementation(libs.ktor.client.core)
}
