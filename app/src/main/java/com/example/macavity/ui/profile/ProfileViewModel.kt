package com.example.macavity.ui.profile

import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.models.local.User
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val user = MutableLiveData<User>()

    fun fetchSelfUser() {
        disposable.add(
            sharedPreferencesManager
                .getCurrentUserId()
                .flatMapPublisher { userId ->
                    userRepository.fetchUserFlowable(userId)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { user ->
                    this.user.postValue(user)
                }
        )
    }

    fun fetchUser(userId: String) {
        disposable.add(userRepository
            .fetchUserFlowable(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { user ->
                this.user.postValue(user)
            }
        )
    }
}
