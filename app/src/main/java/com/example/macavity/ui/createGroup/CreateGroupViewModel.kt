package com.example.macavity.ui.createGroup

import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.repositories.group.GroupRepository
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val sharedPreferencesManager: SharedPreferencesManager
) : BaseViewModel() {

    val groupCreatedSuccess = MutableLiveData<Boolean>(false)

    fun getCurrentUserId() {
        disposable.add(sharedPreferencesManager
            .getCurrentUserId()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userId -> createGroup(userId) }
        )
    }

    private fun createGroup(userId: String) {
        disposable.add(
            groupRepository
                .createGroup(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { groupCreatedSuccess.value = true }
        )
    }
}