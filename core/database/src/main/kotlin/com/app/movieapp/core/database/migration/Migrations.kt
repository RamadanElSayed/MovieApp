package com.app.movieapp.core.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS `movies`")
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `movies` (" +
                "`id` INTEGER NOT NULL, `category` TEXT NOT NULL, `title` TEXT NOT NULL, " +
                "`overview` TEXT NOT NULL, `posterPath` TEXT, `backdropPath` TEXT, " +
                "`voteAverage` REAL NOT NULL, `releaseDate` TEXT NOT NULL, `popularity` REAL NOT NULL, " +
                "`page` INTEGER NOT NULL, `positionInPage` INTEGER NOT NULL, " +
                "PRIMARY KEY(`id`, `category`))",
        )
        db.execSQL("DROP TABLE IF EXISTS `movie_remote_keys`")
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `movie_remote_keys` (" +
                "`movieId` INTEGER NOT NULL, `category` TEXT NOT NULL, " +
                "`prevKey` INTEGER, `nextKey` INTEGER, `lastUpdated` INTEGER NOT NULL, " +
                "PRIMARY KEY(`movieId`, `category`))",
        )
    }
}

val MovieAppMigrations: Array<Migration> = arrayOf(MIGRATION_1_2)
