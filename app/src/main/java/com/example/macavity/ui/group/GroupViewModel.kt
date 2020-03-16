package com.example.macavity.ui.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.models.local.User
import com.example.macavity.data.repositories.group.GroupRepository
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.Flowable
import javax.inject.Inject

class GroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val sharedPreferencesManager: SharedPreferencesManager
) : BaseViewModel() {

    private val groupFlowable = sharedPreferencesManager
        .getCurrentUserId()
        .flatMapPublisher { userRepository.fetchUserFlowable(it) }
        .flatMap { groupRepository.fetchGroupFlowable(it.groupId!!) }

    val group = LiveDataReactiveStreams.fromPublisher(groupFlowable)

    private val memberIdsFlowable = groupFlowable
        .map { it.members }
    
    val members: MutableLiveData<MutableList<User>> by lazy {
        MutableLiveData<MutableList<User>>(mutableListOf<User>())
    }

    fun fetchMembers() {
        disposable.add(memberIdsFlowable
            .subscribe { getMembers(it) }
        )
    }

    private fun getMembers(memberIds: List<String>) {
        memberIds.forEach {
            disposable.add(userRepository.fetchUserFlowable(it)
                .distinctUntilChanged()
                .subscribe{ user ->
                    members.value!!.add(user)
                    members.postValue(members.value)
                }
            )
        }
    }

}
