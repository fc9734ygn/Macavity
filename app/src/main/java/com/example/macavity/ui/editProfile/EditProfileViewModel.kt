package com.example.macavity.ui.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.User
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val currentUser = MutableLiveData<User?>(null)
    val profileUpdatedSuccess =  MutableLiveData<Boolean>(false)
    lateinit var home: Location
    lateinit var destination: Location

    fun saveProfileChanges(
        name: String,
        avatarUrl: String,
        email: String,
        phoneNumber: String,
        isDriver: Boolean,
        carNumberPlate: String?,
        carFreeSeats: Int?
    ) {

        disposable.add(
            userRepository.updateUserProfile(
                currentUser.value!!.id,
                name,
                home,
                destination,
                avatarUrl,
                email,
                phoneNumber,
                isDriver,
                carNumberPlate,
                carFreeSeats
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { profileUpdatedSuccess.postValue(true) }
        )


    }

    fun fetchUser() {
        disposable.add(sharedPreferencesManager
            .getCurrentUserId()
            .flatMap { userId ->
                userRepository.fetchUserSingle(userId)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { user ->
                currentUser.setValue(user)
            }
        )
    }
}
