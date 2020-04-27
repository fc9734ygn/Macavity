package com.example.macavity.data

import android.content.SharedPreferences
import com.example.macavity.utils.SP_USER_ID
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun getCurrentUserId(): Single<String> {
        return Single.defer {
            Single.just<String>(
                sharedPreferences.getString(
                    SP_USER_ID,
                    ""
                )
            )
        }
    }

    fun saveCurrentUserId(id: String): Completable {
        return Completable.fromAction {
            sharedPreferences.edit().putString(SP_USER_ID, id).apply()
        }
    }

    fun clearData(): Completable {
        return Completable.fromAction {
            sharedPreferences.edit().clear().apply()
        }
    }
}