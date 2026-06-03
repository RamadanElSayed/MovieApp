dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    // NOTE: deliberately NOT declaring a `libs` version catalog here. An included build that
    // creates a catalog named `libs` shadows the root build's `libs` accessor inside build
    // script bodies. The convention plugins read the catalog at runtime via the root project's
    // VersionCatalogsExtension (see ProjectExtensions.kt `libs`), so they still use it.
}

rootProject.name = "build-logic"
include(":convention")
