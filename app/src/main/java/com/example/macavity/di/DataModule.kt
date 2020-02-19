package com.example.macavity.di

import android.content.Context
import android.content.SharedPreferences
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.data.repositories.user.UserRepositoryImpl
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class DataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.packageName,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    fun providesRootDatabase(): DatabaseReference {
        val root = FirebaseDatabase.getInstance().reference
        root.keepSynced(false)
        return root
    }

    //region repositories

    @Provides
    fun providesUserRepository(databaseReference: DatabaseReference): UserRepository {
        return UserRepositoryImpl(databaseReference)
    }

    //endregion
}