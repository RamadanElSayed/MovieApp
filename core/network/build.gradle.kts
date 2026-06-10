import java.util.Properties

plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.koin)
}

val localProps =
    Properties().apply {
        val f = rootProject.file("local.properties")
        if (f.exists()) f.inputStream().use { load(it) }
    }

android {
    namespace = "com.app.movieapp.core.network"

    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        buildConfigField(
            "String",
            "TMDB_BASE_URL",
            "\"${localProps.getProperty("TMDB_BASE_URL", "https://api.themoviedb.org/3/")}\"",
        )
        buildConfigField(
            "String",
            "TMDB_IMAGE_BASE_URL",
            "\"${localProps.getProperty("TMDB_IMAGE_BASE_URL", "https://image.tmdb.org/t/p/")}\"",
        )

        buildConfigField(
            "String",
            "TMDB_ACCESS_TOKEN",
            "\"${localProps.getProperty("TMDB_ACCESS_TOKEN", "")}\"",
        )
    }
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.auth)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.ktor.client.mock)
}
