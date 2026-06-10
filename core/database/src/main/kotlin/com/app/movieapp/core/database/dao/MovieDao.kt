package com.app.movieapp.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.movieapp.core.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE category = :category ORDER BY page ASC, positionInPage ASC")
    fun pagingSource(category: String): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    fun observeById(id: Int): Flow<MovieEntity?>

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM movies WHERE category = :category")
    suspend fun clearCategory(category: String)
}
