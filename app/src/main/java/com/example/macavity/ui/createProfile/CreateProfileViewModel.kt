package com.example.macavity.ui.createProfile

import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.models.Location
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateProfileViewModel @Inject constructor(private val userRepo: UserRepository) :
    BaseViewModel() {

    lateinit var home: Location
    lateinit var destination: Location
    val profileCreatedSuccess = MutableLiveData<Boolean>(false)

    fun createProfile(
        userId: String,
        name: String,
        avatarUrl: String,
        email: String,
        phoneNumber: String,
        isDriver: Boolean,
        carNumberPlate: String?,
        carFreeSeats: Int?
    ) {

        disposable.add(
            userRepo.createUser(
                userId,
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
                .subscribe { profileCreatedSuccess.value = true }
        )

    }
}