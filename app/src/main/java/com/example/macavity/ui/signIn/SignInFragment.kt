package com.example.macavity.ui.signIn

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.macavity.R
import com.example.macavity.ui.base.AuthFragment
import com.example.macavity.ui.home.HomeActivity_
import com.example.macavity.utils.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment


@EFragment(resName = "fragment_sign_in")
open class SignInFragment : AuthFragment() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var vm: SignInViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var photoUrl: String

    private val userProfileStateObserver = Observer<SignInViewModel.UserProfileState?> {
        when (it) {
            null -> {
                //do nothing
            }
            SignInViewModel.UserProfileState.COMPLETE -> {
                HomeActivity_.intent(this).start()
            }
            SignInViewModel.UserProfileState.NOT_IN_GROUP -> {
                findNavController().navigate(R.id.action_signInFragment__to_createGroupFragment_)
            }
            SignInViewModel.UserProfileState.NOT_EXISTENT -> {
                val action =
                    SignInFragment_Directions.actionSignInFragmentToCreateProfileFragment(
                        auth.uid!!,
                        photoUrl
                    )
                findNavController().navigate(action)
            }
        }
    }

    @AfterViews
    fun afterViews() {
        buildGoogleSignInClient()
        vm = ViewModelProviders.of(this, viewModelFactory).get(SignInViewModel::class.java)
        auth = FirebaseAuth.getInstance()
        initToolbar()
    }

    override fun onStart() {
        super.onStart()
        checkAccountStatus()
    }

    private fun initToolbar() {
        toolbar.setTitle(getString(R.string.sign_in_toolbar_title))
    }

    private fun checkAccountStatus() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            sign_in_button.visibility = View.VISIBLE
            sign_in_button.setSize(SignInButton.SIZE_WIDE)
        } else {
            HomeActivity_.intent(this).start()
        }
    }

    private fun buildGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client))
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    @Click(resName = ["sign_in_button"])
    fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            photoUrl = account!!.photoUrl.toString()
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            toast(e.message)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    vm.checkIfUserProfileExists(auth.uid!!)
                    vm.userProfileState.observe(this, userProfileStateObserver)
                } else {
                    toast(task.exception?.message)
                }
            }
    }
}
