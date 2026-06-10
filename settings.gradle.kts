pluginManagement {
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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
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

include(":core:design-system")
include(":core:navigation")
include(":core:contract")
include(":core:network")
include(":core:database")

include(":common:domain")
include(":common:data")
include(":common:presentation")

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
