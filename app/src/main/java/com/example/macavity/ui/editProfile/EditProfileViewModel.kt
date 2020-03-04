package com.example.macavity.ui.editProfile

import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.models.local.User
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val currentUser = MutableLiveData<User?>(null)

    fun fetchUser() {
        disposable.add(sharedPreferencesManager
            .getCurrentUserId()
            .flatMap { userId ->
                userRepository.fetchUserSingle(userId)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { user ->
                currentUser.setValue(user)
            }
        )
    }
}
