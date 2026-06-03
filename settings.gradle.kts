pluginManagement {
    // Build composite: the convention plugins live in :build-logic and are consumed by every module.
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MovieApp"

include(":app")

// ---- Core (shared infrastructure — the innermost ring) ----
include(":core:design-system")
include(":core:navigation")
include(":core:contract")
include(":core:network")
include(":core:database")

// ---- Common (layer-specific shared code; sits ABOVE core, BELOW features) ----
include(":common:domain")
include(":common:data")
include(":common:presentation")

// ---- Features (each a full Clean Architecture split: domain / data / presentation) ----
include(":feature:movies-list:domain")
include(":feature:movies-list:data")
include(":feature:movies-list:presentation")

include(":feature:movie-details:domain")
include(":feature:movie-details:presentation")

include(":feature:search:domain")
include(":feature:search:data")
include(":feature:search:presentation")

include(":feature:favorites:domain")
include(":feature:favorites:data")
include(":feature:favorites:presentation")

include(":feature:settings:domain")
include(":feature:settings:data")
include(":feature:settings:presentation")
