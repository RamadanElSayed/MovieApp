plugins {
    alias(libs.plugins.movieapp.kotlin.library)
}
// The inter-feature communication layer: shared interfaces + shared models ONLY.
// Pure Kotlin so any layer (domain/data/presentation) of any feature can depend on it.
// Never depends on a feature; features depend on it (dependency inversion).

dependencies {
    // Outcome/AppError appear in contract signatures (e.g. MovieProvider) -> expose as api.
    api(project(":common:domain"))
    // Flow appears in contract signatures.
    api(libs.kotlinx.coroutines.core)
}
