package com.app.movieapp.common.domain.usecase

import kotlinx.coroutines.flow.Flow

interface UseCase<in P, out R> {
    suspend operator fun invoke(params: P): R
}

interface FlowUseCase<in P, out R> {
    operator fun invoke(params: P): Flow<R>
}
