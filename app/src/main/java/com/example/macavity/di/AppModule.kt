package com.example.macavity.di

import android.content.Context
import android.content.SharedPreferences
import com.example.macavity.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    fun provideApplicationContext(application: App): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.packageName,
            Context.MODE_PRIVATE
        )
    }
}