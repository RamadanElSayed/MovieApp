import com.app.movieapp.buildlogic.catalogLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        dependencies {
            val bom = catalogLibs.findLibrary("koin-bom").get()
            add("implementation", platform(bom))
            add("implementation", catalogLibs.findLibrary("koin-core").get())
            add("implementation", catalogLibs.findLibrary("koin-android").get())
            add("testImplementation", platform(bom))
            add("testImplementation", catalogLibs.findLibrary("koin-test").get())
        }
    }
}
