package com.app.movieapp.buildlogic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Accessor for the shared `libs` version catalog from inside convention plugins.
 *
 * IMPORTANT: this lives in a named package and is intentionally NOT called `libs`. A top-level
 * `val Project.libs` in the default package would land on every module's build-script compile
 * classpath (build scripts compile in the default package) and SHADOW Gradle's generated `libs`
 * accessor — making `libs.androidx`, `libs.koin`, … fail to resolve in every `build.gradle.kts`.
 */
val Project.catalogLibs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")
