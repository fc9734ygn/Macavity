package com.example.macavity.ui.createGroup

import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.repositories.group.GroupRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val sharedPreferencesManager: SharedPreferencesManager
) : BaseViewModel() {

    val userIsInGroup = MutableLiveData<Boolean>(false)

    fun createGroup() {
        disposable.add(sharedPreferencesManager
            .getCurrentUserId()
            .flatMapCompletable { userId ->
                groupRepository
                    .createGroup(userId)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userIsInGroup.value = true }
        )
    }

    fun joinGroup(groupId: String) {
        disposable.add(sharedPreferencesManager
            .getCurrentUserId()
            .flatMapCompletable { userId ->
                groupRepository
                    .joinGroup(groupId, userId)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userIsInGroup.value = true }
        )
    }
}