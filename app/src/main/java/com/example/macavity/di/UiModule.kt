package com.example.macavity.di

import com.example.macavity.ui.splash.SplashActivity_
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    // Activities
    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity_
}