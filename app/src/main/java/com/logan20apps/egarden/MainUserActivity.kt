package com.logan20apps.egarden

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main_user.*
import kotlinx.android.synthetic.main.app_bar_main_user.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class MainUserActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        emarket()

        FirebaseDatabase.getInstance().reference.child("market").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (msgsnap in p0.children) {
                    val arr = JSONArray(msgsnap.value.toString())
                    Market.list.clear()
                    (0 until arr.length())
                            .map { JSONObject(arr.get(it).toString()) }
                            .forEach { Market.list.add(FarmerInventoryItem.getObjFromJson(it.toString())) }
                    if (supportFragmentManager.findFragmentById(R.id.fl_content) is UserMarketFragment){
                        emarket()
                    }
                    Log.w("DATA","I hv data")
                    Log.w("DATA", Arrays.toString(Market.list.toArray()))
                }
            }
        })


    }

    private fun emarket() {
        supportActionBar?.title="E-Market"
        nav_view.setCheckedItem(R.id.nav_emarket)
        supportFragmentManager.beginTransaction().replace(R.id.fl_content,UserMarketFragment()).commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_emarket->{
                emarket()
            }
            R.id.nav_history->{
                supportActionBar?.title="History"
                nav_view.setCheckedItem(R.id.nav_emarket)
                supportFragmentManager.beginTransaction().replace(R.id.fl_content, HistoryFragment()).commit()
            }
            R.id.nav_messages->{
                supportActionBar?.title="Messages"
                nav_view.setCheckedItem(R.id.nav_messages)
                supportFragmentManager.beginTransaction().replace(R.id.fl_content, MessageFragment()).commit()
            }
            R.id.nav_logout->{
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@MainUserActivity, LoginActivity::class.java))
                finish()
            }
            R.id.nav_profile->{
                supportActionBar?.title="Profile"
                nav_view.setCheckedItem(R.id.nav_profile)
                supportFragmentManager.beginTransaction().replace(R.id.fl_content,FarmerProfileFragment()).commit()
            }
            R.id.nav_invite->{
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Invite a Friend")
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey there I'm using this app. Check it out: https://play.google.com/store/apps/details?id="+packageName)
                startActivity(Intent.createChooser(sharingIntent,"Invite a Friend"))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
