package com.example.macavity.ui.signIn

import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferencesManager: SharedPreferencesManager
) :
    BaseViewModel() {

    enum class UserProfileState {
        NOT_EXISTENT, NOT_IN_GROUP, COMPLETE
    }

    val userProfileState = MutableLiveData<UserProfileState?>(null)

    fun checkIfUserProfileExists(userId: String) {
        disposable.add(
            userRepository
                .checkIfUserExists(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { userProfileExists ->
                    if (!userProfileExists) {
                        userProfileState.value = UserProfileState.NOT_EXISTENT
                    } else {
                        saveUserIdLocally(userId)
                    }
                }
        )
    }

    private fun saveUserIdLocally(userId: String) {
        disposable.add(sharedPreferencesManager
            .saveCurrentUserId(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkIfUserIsInGroup(userId)
            }
        )
    }

    private fun checkIfUserIsInGroup(userId: String) {
        disposable.add(userRepository
            .checkIfUserIsInGroup(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userIsInGroup ->
                if (userIsInGroup) {
                    userProfileState.value = UserProfileState.COMPLETE
                } else {
                    userProfileState.value = UserProfileState.NOT_IN_GROUP
                }
            }
        )
    }
}