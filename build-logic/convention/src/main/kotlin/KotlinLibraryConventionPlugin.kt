import com.app.movieapp.buildlogic.catalogLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Pure-Kotlin/JVM library convention — used by **domain** layers and core:common-domain.
 * No Android, no framework: keeps domain code testable on the JVM and KMP-friendly.
 */
class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.jvm")
        configureKotlinJvm()

        dependencies {
            add("implementation", catalogLibs.findLibrary("kotlinx-coroutines-core").get())
            // JUnit 5 (Jupiter) on the JUnit Platform; the launcher is required at test runtime.
            add("testImplementation", platform(catalogLibs.findLibrary("junit-bom").get()))
            add("testImplementation", catalogLibs.findLibrary("junit-jupiter").get())
            add("testRuntimeOnly", catalogLibs.findLibrary("junit-platform-launcher").get())
            add("testImplementation", catalogLibs.findLibrary("kotlinx-coroutines-test").get())
            add("testImplementation", catalogLibs.findLibrary("turbine").get())
            add("testImplementation", catalogLibs.findLibrary("mockk").get())
        }
    }
}
