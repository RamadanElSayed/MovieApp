package com.app.movieapp.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory
import com.app.movieapp.feature.movieslist.domain.repository.MoviesRepository

/**
 * Background refresh of the popular-movies cache. Injected by Koin's WorkManager factory.
 * Returns retry() on failure so WorkManager applies exponential backoff — the cache is never wiped.
 */
class SyncWorker(
    context: Context,
    params: WorkerParameters,
    private val moviesRepository: MoviesRepository,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result =
        when (moviesRepository.refresh(MovieCategory.POPULAR)) {
            is Outcome.Success -> Result.success()
            is Outcome.Failure -> Result.retry()
        }
}
