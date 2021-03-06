package com.example.macavity.ui.addJourney

import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.User
import com.example.macavity.data.repositories.journey.JourneyRepository
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import com.example.macavity.utils.secondsToMillis
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import javax.inject.Inject


class AddJourneyViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val journeysRepository: JourneyRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val journeyCreatedSuccess = MutableLiveData(false)
    val currentUser = MutableLiveData<User>()
    lateinit var startingLocation: Location
    lateinit var destination: Location
    lateinit var dateTime: LocalDateTime

    fun fetchUser() {
        disposable.add(sharedPreferencesManager
            .getCurrentUserId()
            .flatMap { userId ->
                userRepository.fetchUserSingle(userId)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { user ->
                currentUser.postValue(user)
            }
        )
    }

    fun createJourney(
        freeSeats: Int,
        note: String?
    ) {

        val timeStamp = secondsToMillis(dateTime.atZone(ZoneId.systemDefault()).toEpochSecond())

        disposable.add(
            journeysRepository.createJourney(
                currentUser.value!!.groupId!!,
                currentUser.value!!.id,
                freeSeats,
                timeStamp,
                note,
                startingLocation,
                destination,
                currentUser.value!!.avatarUrl,
                currentUser.value!!.driverStat
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { journeyCreatedSuccess.postValue(true) }
        )
    }
}

