import com.app.movieapp.buildlogic.catalogLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

/**
 * Convention for a feature's **presentation** module.
 *
 * Wires the layer-matched dependencies the strict module rules allow a presentation layer to use:
 *   presentation -> common:presentation, core:design-system, core:navigation, core:contract
 *
 * It deliberately does NOT grant access to common:data / common:domain / core:network /
 * core:database — those belong to the data/domain layers and live in sibling modules.
 */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("movieapp.android.library")
            apply("movieapp.android.compose")
            apply("movieapp.koin")
        }

        dependencies {
            add("implementation", project(":common:presentation"))
            add("implementation", project(":core:design-system"))
            add("implementation", project(":core:navigation"))
            add("implementation", project(":core:contract"))

            add("implementation", catalogLibs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
            add("implementation", catalogLibs.findLibrary("androidx-navigation3-runtime").get())
            add("implementation", catalogLibs.findLibrary("androidx-navigation3-ui").get())
            add("implementation", catalogLibs.findLibrary("androidx-lifecycle-viewmodel-navigation3").get())
            add("implementation", catalogLibs.findLibrary("androidx-paging-compose").get())
            add("implementation", catalogLibs.findLibrary("koin-androidx-compose").get())
            add("implementation", catalogLibs.findLibrary("coil-compose").get())

            add("androidTestImplementation", catalogLibs.findLibrary("androidx-junit").get())
        }
    }
}
