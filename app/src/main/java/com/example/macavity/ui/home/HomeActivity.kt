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
import kotlinx.android.synthetic.main.header_drawer.*
import kotlinx.android.synthetic.main.header_drawer.view.*
import org.androidannotations.annotations.AfterViews
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
        bottom_nav.setupWithNavController(navController)
        setDrawerHeaderData()
    }

    private fun setDrawerHeaderData(){
        //TODO: use real data 
        val header: View = nav_view.getHeaderView(0)
        header.header_user_name.text = "John Smith"
        Glide.with(this)
            .load("https://media.gettyimages.com/photos/businessman-wearing-eyeglasses-picture-id825083358?b=1&k=6&m=825083358&s=612x612&w=0&h=SV2xnROuodWTh-sXycr-TULWi-bdlwBDXJkcfCz2lLc=")
            .circleCrop()
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_clear)
            .into(header.header_profile_image)
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

    fun openDrawer(){
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
