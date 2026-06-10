package com.app.movieapp.core.database.migration

import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.app.movieapp.core.database.MovieAppDatabase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieAppMigrationTest {
    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        MovieAppDatabase::class.java,
    )

    @Test
    fun migrate1To2_preservesFavoritesAndRebuildsCache() {
        helper.createDatabase(TEST_DB, 1).apply {
            execSQL(
                "INSERT INTO favorite_movies (movieId, favoritedAt, pendingSync) " +
                    "VALUES (42, 1700000000000, 1)",
            )
            execSQL(
                "INSERT INTO movies (id, title, overview, posterPath, backdropPath, " +
                    "voteAverage, releaseDate, popularity, page, positionInPage) " +
                    "VALUES (1, 'Old', 'cached overview', NULL, NULL, 8.0, '2020-01-01', 1.0, 1, 0)",
            )
            close()
        }

        val db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)

        db.query("SELECT movieId FROM favorite_movies").use { cursor ->
            assertEquals(1, cursor.count)
            assertTrue(cursor.moveToFirst())
            assertEquals(42, cursor.getInt(0))
        }

        db.query("SELECT * FROM movies").use { cursor ->
            assertEquals(0, cursor.count)
        }

        db.query("PRAGMA table_info(movies)").use { cursor ->
            val columns = buildList {
                while (cursor.moveToNext()) add(cursor.getString(cursor.getColumnIndexOrThrow("name")))
            }
            assertTrue(columns.contains("category"))
        }

        db.close()
    }

    private companion object {
        const val TEST_DB = "migration-test"
    }
}
