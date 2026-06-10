import com.app.movieapp.buildlogic.catalogLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.jvm")
        configureKotlinJvm()

        dependencies {
            add("implementation", catalogLibs.findLibrary("kotlinx-coroutines-core").get())

            add("testImplementation", platform(catalogLibs.findLibrary("junit-bom").get()))
            add("testImplementation", catalogLibs.findLibrary("junit-jupiter").get())
            add("testRuntimeOnly", catalogLibs.findLibrary("junit-platform-launcher").get())
            add("testImplementation", catalogLibs.findLibrary("kotlinx-coroutines-test").get())
            add("testImplementation", catalogLibs.findLibrary("turbine").get())
            add("testImplementation", catalogLibs.findLibrary("mockk").get())
        }
    }
}
