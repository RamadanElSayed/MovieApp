package com.app.movieapp

import android.app.Application
import com.app.movieapp.di.allModules
import com.app.movieapp.sync.SyncScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApplication)
            workManagerFactory()
            modules(allModules)
        }
        // Schedule offline-first background refresh with backoff.
        SyncScheduler.schedulePeriodicSync(this)
    }
}
