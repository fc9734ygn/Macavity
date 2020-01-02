package com.example.macavity.ui.splash

import androidx.lifecycle.ViewModelProviders
import com.example.macavity.ui.base.BaseActivity
import com.example.macavity.ui.signIn.SignInActivity_
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import timber.log.Timber
import java.util.concurrent.TimeUnit


@EActivity(resName = "activity_splash")
open class SplashActivity : BaseActivity() {

    private lateinit var vm: SplashViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory)[SplashViewModel::class.java]

//        Completable.complete()
//            .delay(3, TimeUnit.SECONDS)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe ({SignInActivity_.intent(this).start()},{showError(it.message)})

            SignInActivity_.intent(this).start()
    }

}
