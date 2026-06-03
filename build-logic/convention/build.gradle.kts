plugins {
    `kotlin-dsl`
}

group = "com.app.movieapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // The convention plugins call into the Android, Kotlin, KSP and Room Gradle plugin APIs.
    // Coordinates are hardcoded here (kept in sync with gradle/libs.versions.toml) so that
    // :build-logic does NOT declare its own `libs` version catalog — declaring a same-named
    // catalog in an included build shadows the main build's `libs` accessor inside build script
    // bodies (manifesting as "Unresolved reference: androidx/koin/..." in every module).
    compileOnly("com.android.tools.build:gradle:9.0.1")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.0")
    compileOnly("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.0-2.0.2")
    compileOnly("org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.2.0")
    compileOnly("androidx.room:room-gradle-plugin:2.7.0")
}

// Register each convention plugin so modules can apply it by id, e.g.
//   plugins { alias(libs.plugins.movieapp.android.feature) }
gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "movieapp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "movieapp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "movieapp.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "movieapp.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "movieapp.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
        register("androidRoom") {
            id = "movieapp.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("koin") {
            id = "movieapp.koin"
            implementationClass = "KoinConventionPlugin"
        }
    }
}
