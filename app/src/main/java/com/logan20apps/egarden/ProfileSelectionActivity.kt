package com.logan20apps.egarden

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import kotlinx.android.synthetic.main.activity_profile_selection.*


class ProfileSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_selection)

        ib_farmerselection.setOnClickListener{
            val returnIntent = Intent()
            returnIntent.putExtra("result", UserType.FARMER.toString())
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        ib_customer.setOnClickListener{
            val returnIntent = Intent()
            returnIntent.putExtra("result", UserType.CUSTOMER.toString())
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        setResult(Activity.RESULT_CANCELED, returnIntent)
        finish()
    }
}
