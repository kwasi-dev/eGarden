package com.logan20apps.testrun

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_farmer.*
import kotlinx.android.synthetic.main.app_bar_main_farmer.*

class MainFarmerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_farmer)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        //tv_username.text=FirebaseAuth.getInstance().currentUser?.displayName+""
        nav_view.setNavigationItemSelectedListener(this)

        dashboard()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            AlertDialog.Builder(this@MainFarmerActivity)
                    .setMessage("Are you sure you want to exit?")
                    .setTitle("Exit?")
                    .setPositiveButton(android.R.string.yes) { _, _ -> super.onBackPressed() }
                    .setNegativeButton(android.R.string.no,null)
                    .show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_dashboard->{
                dashboard()
            }
            R.id.nav_inventory->{
                supportActionBar?.title="Inventory"
                nav_view.setCheckedItem(R.id.nav_inventory)
                supportFragmentManager.beginTransaction().replace(R.id.fl_content,FarmerInventoryFragment()).commit()
            }
            R.id.nav_schedule->{
                supportActionBar?.title="Schedule"
                nav_view.setCheckedItem(R.id.nav_dashboard)
                supportFragmentManager.beginTransaction().replace(R.id.fl_content,FarmerScheduleFragment()).commit()
            }
            R.id.nav_logout->{
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@MainFarmerActivity, LoginActivity::class.java))
                finish()
            }
            R.id.nav_invite->{
                val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Invite a Friend")
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey there I'm using this app. Check it out: https://play.google.com/store/apps/details?id="+packageName)
                startActivity(Intent.createChooser(sharingIntent,"Invite a Friend"))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun dashboard(){
        supportActionBar?.title="Dashboard"
        nav_view.setCheckedItem(R.id.nav_dashboard)
        supportFragmentManager.beginTransaction().replace(R.id.fl_content,FarmerDashboardFragment()).commit()
    }
}
