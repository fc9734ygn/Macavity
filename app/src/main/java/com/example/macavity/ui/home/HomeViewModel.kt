package com.example.macavity.ui.home

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val signOutSuccess = MutableLiveData(false)

    private val userFlowable = sharedPreferencesManager
        .getCurrentUserId()
        .flatMapPublisher { userRepository.fetchUserFlowable(it) }

    val userLiveData = LiveDataReactiveStreams.fromPublisher(userFlowable)

    fun signOut() {
        disposable.add(
            sharedPreferencesManager.clearData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    signOutSuccess.postValue(true)
                }
        )
    }
}