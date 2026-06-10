import com.app.movieapp.buildlogic.catalogLibs
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        val commonExtension = when {
            pluginManager.hasPlugin("com.android.application") ->
                extensions.getByType<ApplicationExtension>()
            pluginManager.hasPlugin("com.android.library") ->
                extensions.getByType<LibraryExtension>()
            else -> error("movieapp.android.compose requires an Android application or library plugin")
        }
        configureCompose(commonExtension)
    }

    private fun Project.configureCompose(commonExtension: CommonExtension) {
        commonExtension.apply {
            buildFeatures.compose = true

            dependencies {
                val bom = catalogLibs.findLibrary("androidx-compose-bom").get()
                add("implementation", platform(bom))
                add("androidTestImplementation", platform(bom))

                add("implementation", catalogLibs.findLibrary("androidx-compose-ui").get())
                add("implementation", catalogLibs.findLibrary("androidx-compose-ui-graphics").get())
                add("implementation", catalogLibs.findLibrary("androidx-compose-ui-tooling-preview").get())
                add("implementation", catalogLibs.findLibrary("androidx-compose-material3").get())
                add("implementation", catalogLibs.findLibrary("androidx-compose-material-icons-extended").get())
                add("implementation", catalogLibs.findLibrary("androidx-lifecycle-runtime-compose").get())

                add("debugImplementation", catalogLibs.findLibrary("androidx-compose-ui-tooling").get())
                add("debugImplementation", catalogLibs.findLibrary("androidx-compose-ui-test-manifest").get())
                add("androidTestImplementation", catalogLibs.findLibrary("androidx-compose-ui-test-junit4").get())
            }
        }
    }
}
