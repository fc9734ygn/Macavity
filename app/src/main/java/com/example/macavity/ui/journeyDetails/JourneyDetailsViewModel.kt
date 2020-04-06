package com.example.macavity.ui.journeyDetails

import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.models.local.JourneyDetails
import com.example.macavity.data.models.local.User
import com.example.macavity.data.repositories.journey.JourneyRepository
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class JourneyDetailsViewModel @Inject constructor(
    private val journeyRepository: JourneyRepository,
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userRepository: UserRepository
) : BaseViewModel() {

    enum class UserState {
        DRIVER, PASSENGER, NEITHER
    }

    val journeyDetails = MutableLiveData<JourneyDetails>()
    val driver = MutableLiveData<User>()
    val currentUserState = MutableLiveData<UserState>()
    val passengers: MutableLiveData<MutableList<User>> by lazy {
        MutableLiveData<MutableList<User>>(mutableListOf())
    }

    lateinit var currentUser: User
    lateinit var currentUserGroupId: String

    private val currentUserFlowable = sharedPreferencesManager
        .getCurrentUserId()
        .flatMapPublisher {
            userRepository.fetchUserFlowable(it)
        }.map {
            currentUserGroupId = it.groupId!!
            currentUser = it
            it
        }

    fun fetchJourney(journeyId: String) {
        disposable.add(currentUserFlowable
            .flatMap { journeyRepository.fetchJourneyDetails(it.groupId!!, journeyId) }
            .flatMap { journey ->
                journeyDetails.postValue(journey)

                if (!journey.passengerIds.isNullOrEmpty()) {
                    getMembers(journey.passengerIds)
                } else {
                    passengers.postValue(mutableListOf())
                }
                updateCurrentUserState(journey.passengerIds, journey.driverId)
                userRepository.fetchUserFlowable(journey.driverId)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { journeyDriver ->
                driver.postValue(journeyDriver)
            }
        )
    }

    private fun updateCurrentUserState(
        passengerIds: List<String>?,
        driverId: String
    ) {
        val currentUserIsDriver = driverId == currentUser.id
        val currentUserIsPassenger = if (passengerIds.isNullOrEmpty()) {
            false
        } else {
            passengerIds.contains(currentUser.id)
        }

        val userState = when {
            currentUserIsDriver -> {
                UserState.DRIVER
            }
            currentUserIsPassenger -> {
                UserState.PASSENGER
            }
            else -> {
                UserState.NEITHER
            }
        }

        this.currentUserState.postValue(userState)
    }

    private fun getMembers(memberIds: List<String>) {
        val list = mutableListOf<User>()
        memberIds.forEach {
            disposable.add(userRepository.fetchUserFlowable(it)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { user ->
                    list.add(user)
                    passengers.postValue(list)
                }
            )
        }
    }

    fun bookSeat() {
        disposable.add(
            journeyRepository
                .bookSeat(currentUserGroupId, journeyDetails.value!!.id, currentUser.id, currentUser.passengerStat)
                .subscribe()
        )
    }

    fun cancelBooking() {
        disposable.add(
            journeyRepository.cancelBooking(
                currentUserGroupId,
                journeyDetails.value!!.id,
                currentUser.id,
                currentUser.passengerStat
            ).subscribe {
                fetchJourney(journeyDetails.value!!.id)
            }
        )
    }
}