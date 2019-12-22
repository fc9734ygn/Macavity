package com.example.macavity.di

import android.content.Context
import com.example.macavity.App
import dagger.Module
import dagger.Provides


@Module
class AppModule {

    @Provides
    fun provideApplicationContext(application: App): Context {
        return application.applicationContext
    }
}