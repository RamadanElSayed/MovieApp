plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.koin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.app.movieapp.feature.movieslist.data"
}

dependencies {
    implementation(project(":feature:movies-list:domain"))

    implementation(project(":common:data"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:contract"))

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.room.ktx)

    testImplementation(libs.ktor.client.mock)
}
