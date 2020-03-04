package com.example.macavity.di

import android.content.SharedPreferences
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.repositories.group.GroupRepository
import com.example.macavity.data.repositories.group.GroupRepositoryImpl
import com.example.macavity.data.repositories.journey.JourneyRepository
import com.example.macavity.data.repositories.journey.JourneyRepositoryImpl
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.data.repositories.user.UserRepositoryImpl
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides


@Module
class DataModule {

    @Provides
    fun providesRootDatabase(): DatabaseReference {
        val root = FirebaseDatabase.getInstance().reference
        root.keepSynced(false)
        return root
    }

    @Provides
    fun providesSharedPreferencesManager(sharedPreferences: SharedPreferences): SharedPreferencesManager{
        return SharedPreferencesManager(sharedPreferences)
    }

    //region repositories

    @Provides
    fun providesUserRepository(databaseReference: DatabaseReference): UserRepository {
        return UserRepositoryImpl(databaseReference)
    }

    @Provides
    fun providesGroupRepository(databaseReference: DatabaseReference): GroupRepository {
        return GroupRepositoryImpl(databaseReference)
    }

    @Provides
    fun providesJourneyRepository(databaseReference: DatabaseReference): JourneyRepository {
        return JourneyRepositoryImpl(databaseReference)
    }

    //endregion
}