package com.example.macavity.ui.home

import android.annotation.SuppressLint
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity


@EActivity(resName = "activity_home")
open class HomeActivity : BaseActivity() {

    lateinit var vm: HomeViewModel
    private lateinit var navController: NavController

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]
        initDrawerNavigation()
    }

    private fun initDrawerNavigation(){
        navController = nav_host_fragment.findNavController()
        nav_view.setupWithNavController(navController)
        setDrawerAvatarImg()
    }

    private fun setDrawerAvatarImg(){
        //TODO: use real data 
        val header: View = nav_view.getHeaderView(0)
        val avatarView = header.findViewById<View>(R.id.header_profile_image) as ImageView
        Glide.with(this)
            .load("https://66.media.tumblr.com/4c69fcb24a6d09010e6f818b31eba7c5/tumblr_po8044mLw21truxr0_540.jpg")
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_clear)
            .into(avatarView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val retValue = super.onCreateOptionsMenu(menu)
        if (nav_view == null) {
            menuInflater.inflate(R.menu.menu_drawer, menu)
            return true
        }
        return retValue
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item!!.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                || super.onOptionsItemSelected(item)
    }

    @Click(resName = ["calendar_button"])
    fun goToCalendar(){
        navController.navigate(R.id.calendarFragment_)
    }

    @Click(resName = ["chat_button"])
    fun goToChat(){
        navController.navigate(R.id.chatFragment_)
    }
    
    @Click(resName = ["fab"])
    fun onFabClick(){
        navController.navigate(R.id.mapFragment_)
    }

    fun openDrawer(){
        drawer_layout.openDrawer(GravityCompat.START)
    }

    @SuppressLint("RestrictedApi") //app:behavior_autoHide="false" in XML
    fun toggleBottomNavigation(show: Boolean){
        val desiredVisibility = if(show) View.VISIBLE else View.GONE
        bottom_app_bar.visibility = desiredVisibility
        fab.visibility = desiredVisibility
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
