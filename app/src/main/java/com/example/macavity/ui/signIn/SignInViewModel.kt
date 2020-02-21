package com.example.macavity.ui.signIn

import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class SignInViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    val userExist = MutableLiveData<Boolean?>(null)

    fun checkIfUserProfileExists(userId: String) {
        disposable.add(
            userRepository
                .checkIfUserExists(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ result -> userExist.value = result}
        )
    }
}