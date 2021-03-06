package com.example.macavity.ui.home

import android.app.Activity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.local.User
import com.example.macavity.ui.base.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.header_drawer.view.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity


@EActivity(resName = "activity_home")
open class HomeActivity : BaseActivity() {

    lateinit var vm: HomeViewModel
    private lateinit var navController: NavController
    lateinit var currentUser: User

    private val userObserver = Observer<User> {
        currentUser = it
        setDrawerHeaderData()
    }

    private val localDataClearedSuccessObserver = Observer<Boolean> {
        // signing out
        if (it) {
            val client = GoogleSignIn.getClient(
                this,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            )
            client.revokeAccess().addOnCompleteListener {
                FirebaseAuth.getInstance().signOut()
                this.finishAffinity()
            }
        }
    }

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]
        vm.userLiveData.observe(this, userObserver)
        vm.localDataClearedSuccess.observe(this, localDataClearedSuccessObserver)
        initDrawerNavigation()
    }

    private fun initDrawerNavigation() {
        navController = nav_host_fragment.findNavController()
        nav_view.setupWithNavController(navController)
        bottom_nav.setupWithNavController(navController)
        nav_view.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.sign_out) {
                vm.clearLocalData()
                true
            } else {
                drawer_layout.closeDrawer(nav_view)
                onOptionsItemSelected(item)
            }
        }
    }

    private fun setDrawerHeaderData() {
        val header: View = nav_view.getHeaderView(0)
        header.header_user_name.text = currentUser.name
        Glide.with(this)
            .load(currentUser.avatarUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(header.header_profile_image)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (nav_view == null) {
            menuInflater.inflate(R.menu.menu_drawer, menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return (item!!.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                || super.onOptionsItemSelected(item))
    }

    fun openDrawer() {
        drawer_layout.openDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
