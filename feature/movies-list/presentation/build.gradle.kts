plugins {
    alias(libs.plugins.movieapp.android.feature)
}

android {
    namespace = "com.app.movieapp.feature.movieslist.presentation"
}

dependencies {
    // Same-feature: presentation depends on this feature's domain (use cases + domain model).
    implementation(project(":feature:movies-list:domain"))
    // Cross-feature data is consumed ONLY through contracts (FavoritesProvider) — never the module.
    // (core:contract is already provided by the feature convention plugin.)
}
