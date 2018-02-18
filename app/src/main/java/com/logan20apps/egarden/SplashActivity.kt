package com.logan20apps.egarden

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    private var vis:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        vis =true

        val thread = object : Thread() {
            override fun run() {
                try{
                    Thread.sleep(1500)
                    if(vis){
                        startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
                        finish();
                    }
                }
                catch (e:Exception){}
            }
        }
        thread.start()
    }
    override fun onPause() {
        vis =false
        super.onPause()
    }
}
