package com.example.macavity

import android.content.Context
import com.example.macavity.di.DaggerAppComponent
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree


class App : DaggerApplication() {

    private lateinit var placesClient: PlacesClient

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
        initPlaces()
        initCalendarLib()
        FirebaseDatabase.getInstance().setPersistenceEnabled(false)
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG)
    }

    private fun initCalendarLib(){
        AndroidThreeTen.init(this)
    }

    private fun initPlaces() {
        Places.initialize(applicationContext, getString(R.string.places_api_key))
        placesClient = Places.createClient(this)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}