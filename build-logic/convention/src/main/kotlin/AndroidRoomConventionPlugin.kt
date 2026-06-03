import com.app.movieapp.buildlogic.catalogLibs
import androidx.room.gradle.RoomExtension
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Room + KSP convention. Room 2.7 is Kotlin-Multiplatform-ready and uses the `androidx.room`
 * Gradle plugin together with a generated schema location for migration verification.
 */
class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("androidx.room")
        pluginManager.apply("com.google.devtools.ksp")

        extensions.configure<RoomExtension> {
            // Export schemas so migrations are diffable and testable in CI.
            schemaDirectory("$projectDir/schemas")
        }

        extensions.configure<KspExtension> {
            arg("room.generateKotlin", "true")
        }

        dependencies {
            add("implementation", catalogLibs.findLibrary("androidx-room-runtime").get())
            add("implementation", catalogLibs.findLibrary("androidx-room-ktx").get())
            add("implementation", catalogLibs.findLibrary("androidx-room-paging").get())
            add("ksp", catalogLibs.findLibrary("androidx-room-compiler").get())
            add("testImplementation", catalogLibs.findLibrary("androidx-room-testing").get())
        }
    }
}
