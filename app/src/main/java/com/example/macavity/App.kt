package com.example.macavity

import android.app.Application
import android.content.Context
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}