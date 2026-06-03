plugins {
    alias(libs.plugins.movieapp.kotlin.library)
}
// Pure, framework-free domain primitives shared by every feature's domain layer.
// The `common` layer sits between `core` infrastructure and the `feature` modules.
// :common:domain is the innermost ring — it depends on NOTHING but Kotlin stdlib + coroutines.
