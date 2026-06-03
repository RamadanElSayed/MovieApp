package com.app.movieapp.common.domain.usecase

import kotlinx.coroutines.flow.Flow

/**
 * Base contract for a one-shot suspending use case. Implementations contain a single public
 * `invoke` so call sites read like `getMovies(params)`.
 *
 * [P] = input parameters (use [Unit] when none), [R] = result type.
 */
interface UseCase<in P, out R> {
    suspend operator fun invoke(params: P): R
}

/** Base contract for a streaming use case backed by a [Flow] (offline-first reads from Room). */
interface FlowUseCase<in P, out R> {
    operator fun invoke(params: P): Flow<R>
}
