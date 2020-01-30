package com.example.macavity.di

import com.example.macavity.ui.addJourney.AddJourneyFragment_
import com.example.macavity.ui.auth.AuthActivity_
import com.example.macavity.ui.calendar.CalendarFragment_
import com.example.macavity.ui.chat.ChatFragment_
import com.example.macavity.ui.createProfile.CreateProfileFragment_
import com.example.macavity.ui.editJourney.EditJourneyFragment_
import com.example.macavity.ui.editProfile.EditProfileFragment_
import com.example.macavity.ui.group.GroupFragment_
import com.example.macavity.ui.home.HomeActivity_
import com.example.macavity.ui.journeyDetails.JourneyDetailFragment_
import com.example.macavity.ui.map.MapFragment_
import com.example.macavity.ui.profile.ProfileFragment_
import com.example.macavity.ui.settings.SettingsFragment_
import com.example.macavity.ui.signIn.SignInFragment_
import com.example.macavity.ui.splash.SplashFragment_
import com.example.macavity.ui.tutorial.TutorialFragment_
import com.example.macavity.ui.yourJourneys.YourJourneysFragment_
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    //region Activities
    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity_

    @ContributesAndroidInjector
    abstract fun contributeAuthActivity(): AuthActivity_

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

    @ContributesAndroidInjector
    abstract fun contributeTutorialActivity(): TutorialFragment_

    @ContributesAndroidInjector
    abstract fun contributeCreateProfileActivity(): CreateProfileFragment_

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment_

    @ContributesAndroidInjector
    abstract fun contributeSignInFragment(): SignInFragment_

    @ContributesAndroidInjector
    abstract fun contributeJourneyDetailsFragment(): JourneyDetailFragment_

    @ContributesAndroidInjector
    abstract fun contributeYourJourneysFragment(): YourJourneysFragment_

    //endregion
}