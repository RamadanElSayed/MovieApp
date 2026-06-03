import com.app.movieapp.buildlogic.catalogLibs
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.library")
        // AGP 9 ships built-in Kotlin support and registers the `kotlin` extension itself.
        // Only apply the standalone Kotlin Android plugin if that extension isn't there yet.
        if (extensions.findByName("kotlin") == null) {
            pluginManager.apply("org.jetbrains.kotlin.android")
        }

        extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)
            defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        dependencies {
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
