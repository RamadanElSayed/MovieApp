package com.app.movieapp.common.domain.result

import com.app.movieapp.common.domain.error.AppError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class OutcomeTest {
    @Test
    fun `map transforms success data`() {
        val result = Outcome.Success(2).map { it * 10 }
        assertEquals(Outcome.Success(20), result)
    }

    @Test
    fun `map preserves failure`() {
        val failure: Outcome<Int> = Outcome.Failure(AppError.Network)
        val mapped = failure.map { it * 10 }
        assertEquals(Outcome.Failure(AppError.Network), mapped)
    }

    @Test
    fun `getOrNull returns null on failure`() {
        val failure: Outcome<Int> = Outcome.Failure(AppError.Timeout)
        assertNull(failure.getOrNull())
    }

    @Test
    fun `onSuccess and onFailure run the right branch`() {
        var seen = 0
        Outcome.Success(5).onSuccess { seen = it }.onFailure { seen = -1 }
        assertEquals(5, seen)
    }
}
