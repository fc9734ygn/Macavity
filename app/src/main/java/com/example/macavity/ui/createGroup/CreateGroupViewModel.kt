package com.example.macavity.ui.createGroup

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.repositories.group.GroupRepository
import com.example.macavity.ui.base.BaseViewModel
import com.example.macavity.utils.SP_USER_ID
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    val groupCreatedSuccess = MutableLiveData<Boolean>(false)

    fun getCurrentUserId() {
        disposable.add(Single.defer {
            Single.just<String>(
                sharedPreferences.getString(
                    SP_USER_ID,
                    ""
                )
            )
        }.subscribeOn(Schedulers.io())
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