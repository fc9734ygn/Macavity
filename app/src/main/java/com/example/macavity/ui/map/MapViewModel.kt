package com.example.macavity.ui.map

import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.models.local.User
import com.example.macavity.data.repositories.group.GroupRepository
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val usersRepository: UserRepository,
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val groupRepository: GroupRepository
) :
    BaseViewModel() {

    val members: MutableLiveData<MutableList<User>> by lazy {
        MutableLiveData<MutableList<User>>(mutableListOf())
    }

    private val memberIdsFlowable = sharedPreferencesManager
        .getCurrentUserId()
        .flatMapPublisher { usersRepository.fetchUserFlowable(it) }
        .flatMap { groupRepository.fetchGroupFlowable(it.groupId!!) }
        .map { getMembers(it.members) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe()

    private fun getMembers(memberIds: List<String>) {
        memberIds.forEach {
            disposable.add(usersRepository.fetchUserFlowable(it)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { user ->
                    members.value!!.add(user)
                    members.postValue(members.value)
                }
            )
        }
    }
}
