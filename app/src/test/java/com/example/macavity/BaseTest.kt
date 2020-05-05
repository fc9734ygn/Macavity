package com.example.macavity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

open class BaseTest {

    @Rule
    @JvmField
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Before
    fun initializeMocks() {

        // Initialize the mock annotations
        MockKAnnotations.init(this)

        // This is because RxSchedulersOverrideRule is not enough for some reason
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDownClass(){
        RxAndroidPlugins.reset();
    }
}