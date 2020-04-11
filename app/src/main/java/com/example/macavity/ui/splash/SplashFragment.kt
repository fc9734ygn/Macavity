package com.example.macavity.ui.splash

import android.os.Handler
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.macavity.R
import com.example.macavity.ui.auth.AuthFragment
import com.example.macavity.utils.SPLASH_DELAY_AMOUNT_IN_MILLIS
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment


@EFragment(resName = "fragment_splash")
open class SplashFragment : AuthFragment() {

    private lateinit var vm: SplashViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)
        Handler().postDelayed(
            {
                findNavController().navigate(R.id.action_splashFragment__to_signInFragment_)
            },
            SPLASH_DELAY_AMOUNT_IN_MILLIS
        )
    }
}
