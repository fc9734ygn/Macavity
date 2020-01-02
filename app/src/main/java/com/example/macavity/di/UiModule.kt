package com.example.macavity.di

import com.example.macavity.ui.home.HomeActivity_
import com.example.macavity.ui.signIn.SignInActivity_
import com.example.macavity.ui.splash.SplashActivity_
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    //region Activities
    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity_

    @ContributesAndroidInjector
    abstract fun contributeSignInActivity(): SignInActivity_

    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity_

    //endregion
}