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

    val journeyDetails = MutableLiveData<JourneyDetails>()
    val driver = MutableLiveData<User>()
    val currentUserIsDriver = MutableLiveData<Boolean>()
    val currentUserIsPassenger = MutableLiveData<Boolean>()
    val passengers: MutableLiveData<MutableList<User>> by lazy {
        MutableLiveData<MutableList<User>>(mutableListOf())
    }

    lateinit var currentUserId: String
    lateinit var currentUserGroupId: String

    private val currentUserFlowable = sharedPreferencesManager
        .getCurrentUserId()
        .flatMapPublisher {
            currentUserId = it
            userRepository.fetchUserFlowable(currentUserId)
        }.map {
            currentUserGroupId = it.groupId!!
            it
        }

    fun fetchJourney(journeyId: String) {
        disposable.add(currentUserFlowable
            .flatMap { journeyRepository.fetchJourneyDetails(it.groupId!!, journeyId) }
            .flatMap { journey ->
                journeyDetails.postValue(journey)

                if (!journey.passengerIds.isNullOrEmpty()) {
                    getMembers(journey.passengerIds)
                    currentUserIsPassenger.postValue(journey.passengerIds.contains(currentUserId))
                } else {
                    currentUserIsPassenger.postValue(false)
                }

                userRepository.fetchUserFlowable(journey.driverId)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { journeyDriver ->
                currentUserIsDriver.postValue(journeyDriver.id == currentUserId)
                driver.postValue(journeyDriver)
            }
        )
    }

    private fun getMembers(memberIds: List<String>) {
        memberIds.forEach {
            disposable.add(userRepository.fetchUserFlowable(it)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { user ->
                    passengers.value!!.add(user)
                    passengers.postValue(passengers.value)
                }
            )
        }
    }

    fun bookSeat() {
        disposable.add(journeyRepository
            .bookSeat(currentUserGroupId, journeyDetails.value!!.id, currentUserId)
            .subscribe {}
        )
    }

    fun cancelJourney() {
        //todo: cancel journey
    }

    fun cancelBooking() {
        //todo: cancel booking
    }
}