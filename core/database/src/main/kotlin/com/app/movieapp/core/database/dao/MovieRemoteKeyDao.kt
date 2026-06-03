package com.app.movieapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.movieapp.core.database.entity.MovieRemoteKeyEntity

@Dao
interface MovieRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(keys: List<MovieRemoteKeyEntity>)

    /** The most recently fetched key within a category — drives the next APPEND page. */
    @Query("SELECT * FROM movie_remote_keys WHERE category = :category ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun latest(category: String): MovieRemoteKeyEntity?

    @Query("SELECT MAX(lastUpdated) FROM movie_remote_keys WHERE category = :category")
    suspend fun lastUpdated(category: String): Long?

    @Query("DELETE FROM movie_remote_keys WHERE category = :category")
    suspend fun clearCategory(category: String)
}
