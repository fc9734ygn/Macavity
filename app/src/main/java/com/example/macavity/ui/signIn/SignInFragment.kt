package com.example.macavity.ui.signIn

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.macavity.R
import com.example.macavity.ui.base.AuthFragment
import com.example.macavity.ui.base.BaseActivity
import com.example.macavity.ui.home.HomeActivity_
import com.example.macavity.utils.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.EFragment


@EFragment(resName = "fragment_sign_in")
open class SignInFragment : AuthFragment() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var vm: SignInViewModel
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildGoogleSignInClient()
        vm = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        auth = FirebaseAuth.getInstance()
        auth.uid
    }

    override fun onStart() {
        super.onStart()
        checkAccountStatus()
    }

    private fun checkAccountStatus() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            sign_in_button.visibility = View.VISIBLE
        } else {
            HomeActivity_.intent(this).start()
        }
    }

    private fun buildGoogleSignInClient(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client))
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity!!, gso)
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
            firebaseAuthWithGoogle(account!!)
        } catch (e: ApiException) {
            showError(e.message)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    HomeActivity_.intent(this).start()
                } else {
                    showError(task.exception?.message)
                }
            }
    }
}
