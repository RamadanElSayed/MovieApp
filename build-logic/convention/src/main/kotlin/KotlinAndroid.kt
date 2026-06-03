import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal const val COMPILE_SDK = 36
internal const val MIN_SDK = 24
internal const val TARGET_SDK = 36

/** Shared Android `compileSdk`/`minSdk`/Java/Kotlin configuration for application & library modules. */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension,
) {
    commonExtension.apply {
        compileSdk = COMPILE_SDK

        when (this) {
            is ApplicationExtension -> defaultConfig.minSdk = MIN_SDK
            is LibraryExtension -> defaultConfig.minSdk = MIN_SDK
        }

        compileOptions.apply {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        // Run local unit tests on the JUnit 5 Platform (Jupiter).
        testOptions.unitTests.all { it.useJUnitPlatform() }
    }

    configureKotlin()
}

/** Shared Kotlin/JVM configuration for pure-Kotlin (non-Android) modules. */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    // Run pure-Kotlin/JVM module tests on the JUnit 5 Platform (Jupiter).
    tasks.withType(Test::class.java).configureEach { useJUnitPlatform() }
    configureKotlin()
}

private fun Project.configureKotlin() {
    tasks.withType(KotlinCompile::class.java).configureEach {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
            // Treat warnings as warnings (not errors); opt into common experimental APIs project-wide.
            freeCompilerArgs.addAll(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                // Apply annotations like @StringRes to both the parameter AND the backing field,
                // matching the upcoming Kotlin default (silences KT-73255 migration warnings).
                "-Xannotation-default-target=param-property",
            )
        }
    }
}
