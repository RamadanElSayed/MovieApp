package com.app.movieapp.common.domain.connectivity

import kotlinx.coroutines.flow.Flow

enum class NetworkStatus { AVAILABLE, UNAVAILABLE }

/**
 * Exposes connectivity as a [Flow] to drive offline UI states and trigger sync on reconnect.
 * Interface is pure (lives in domain); the Android implementation lives in common:data.
 */
interface ConnectivityObserver {
    val status: Flow<NetworkStatus>
    fun isCurrentlyAvailable(): Boolean
}
