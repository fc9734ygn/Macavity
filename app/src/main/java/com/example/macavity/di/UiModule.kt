package com.example.macavity.di

import com.example.macavity.ui.addJourney.AddJourneyFragment_
import com.example.macavity.ui.calendar.CalendarFragment_
import com.example.macavity.ui.chat.ChatFragment_
import com.example.macavity.ui.createProfile.CreateProfileActivity
import com.example.macavity.ui.createProfile.CreateProfileActivity_
import com.example.macavity.ui.editJourney.EditJourneyFragment_
import com.example.macavity.ui.editProfile.EditProfileFragment_
import com.example.macavity.ui.group.GroupFragment_
import com.example.macavity.ui.home.HomeActivity_
import com.example.macavity.ui.map.MapFragment_
import com.example.macavity.ui.profile.ProfileFragment_
import com.example.macavity.ui.settings.SettingsFragment_
import com.example.macavity.ui.signIn.SignInActivity_
import com.example.macavity.ui.splash.SplashActivity_
import com.example.macavity.ui.tutorial.TutorialActivity_
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

    @ContributesAndroidInjector
    abstract fun contributeTutorialActivity(): TutorialActivity_

    @ContributesAndroidInjector
    abstract fun contributeCreateProfileActivity(): CreateProfileActivity_

    //endregion

    //region Fragments
    @ContributesAndroidInjector
    abstract fun contributeAddJourneyFragment(): AddJourneyFragment_

    @ContributesAndroidInjector
    abstract fun contributeCalendarFragment(): CalendarFragment_

    @ContributesAndroidInjector
    abstract fun contributeChatFragment(): ChatFragment_

    @ContributesAndroidInjector
    abstract fun contributeEditJourneyFragment(): EditJourneyFragment_

    @ContributesAndroidInjector
    abstract fun contributeEditProfileFragment(): EditProfileFragment_

    @ContributesAndroidInjector
    abstract fun contributeGroupFragment(): GroupFragment_

    @ContributesAndroidInjector
    abstract fun contributeMapFragment(): MapFragment_

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment_

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment_
    //endregion
}