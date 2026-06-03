package com.app.movieapp

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.app.movieapp.ui.MovieAppRoot

/**
 * Single-activity host. Extends [AppCompatActivity] so the per-app language API
 * (`AppCompatDelegate.setApplicationLocales`, invoked from Settings) takes effect, and so the
 * persisted locale is applied automatically on launch. Layout direction (RTL for Arabic) follows
 * the active locale and flows into Compose's `LocalLayoutDirection`.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Let the Material 3 NavigationBar's own color show through the gesture/system nav area
        // instead of the OS-enforced translucent contrast scrim (the cause of the colour mismatch).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent { MovieAppRoot() }
    }
}
