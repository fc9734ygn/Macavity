package com.example.macavity.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.macavity.ui.tutorial.TutorialViewModel
import com.example.macavity.ui.addJourney.AddJourneyViewModel
import com.example.macavity.ui.auth.AuthViewModel
import com.example.macavity.ui.base.BaseViewModel
import com.example.macavity.ui.calendar.CalendarViewModel
import com.example.macavity.ui.chat.ChatViewModel
import com.example.macavity.ui.createProfile.CreateProfileViewModel
import com.example.macavity.ui.editJourney.EditJourneyViewModel
import com.example.macavity.ui.editProfile.EditProfileViewModel
import com.example.macavity.ui.home.HomeViewModel
import com.example.macavity.ui.journeyDetails.JourneyDetailsViewModel
import com.example.macavity.ui.map.MapViewModel
import com.example.macavity.ui.profile.ProfileViewModel
import com.example.macavity.ui.settings.SettingsViewModel
import com.example.macavity.ui.signIn.SignInViewModel
import com.example.macavity.ui.splash.SplashViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    //region Viewmodels
    @Binds
    @IntoMap
    @ViewModelKey(BaseViewModel::class)
    internal abstract fun baseViewModel(viewModel: BaseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun splashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    internal abstract fun signInViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddJourneyViewModel::class)
    internal abstract fun addJourneyViewModel(viewModel: AddJourneyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CalendarViewModel::class)
    internal abstract fun calendarViewModel(viewModel: CalendarViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    internal abstract fun chatViewModel(viewModel: ChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditJourneyViewModel::class)
    internal abstract fun editJourneyViewModel(viewModel: EditJourneyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    internal abstract fun editProfileViewModel(viewModel: EditProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    internal abstract fun mapViewModel(viewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun profileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun settingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TutorialViewModel::class)
    internal abstract fun tutorialViewModel(viewModel: TutorialViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateProfileViewModel::class)
    internal abstract fun createProfileViewModel(viewModel: CreateProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    internal abstract fun authViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(JourneyDetailsViewModel::class)
    internal abstract fun journeyDetailsViewModel(viewModel: JourneyDetailsViewModel): ViewModel

    //Add more ViewModels here
    //endregion
}

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)