package com.example.macavity

import android.content.Context
import com.example.macavity.di.DaggerAppComponent
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree


class App : DaggerApplication() {

    private val applicationInjector = DaggerAppComponent.builder()
    .application(this)
    .build()

    override fun applicationInjector() = applicationInjector


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

    companion object {
        lateinit var instance: App
            private set
    }
}