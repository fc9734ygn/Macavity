package com.example.macavity.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.models.local.User
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val userFlowable = sharedPreferencesManager
        .getCurrentUserId()
        .flatMapPublisher { userRepository.fetchUserFlowable(it) }

    val userLiveData = LiveDataReactiveStreams.fromPublisher(userFlowable)

}