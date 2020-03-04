package com.example.macavity.ui.invite

import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class InviteViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val invitationCode = MutableLiveData<String>()

    fun getInvitationCode() {
        disposable.add(
            sharedPreferencesManager
                .getCurrentUserId()
                .flatMap { userId ->
                    userRepository.fetchUserGroupId(userId)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { code ->
                    if (code.isNullOrBlank()) {
                        //todo: handle error
                    } else {
                        invitationCode.value = code
                    }
                }
        )
    }
}