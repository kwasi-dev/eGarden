package com.logan20apps.testrun

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val tiets = arrayOf(tiet_fname,tiet_lname,tiet_email,tiet_password,tiet_password_confirm,tiet_adddress_line1,tiet_adddress_line2,tiet_city,tiet_country)
        val tils = arrayOf(til_fname,til_lname,til_email,til_password,til_password_confirm,til_adddress_line1,til_adddress_line2,til_city,til_country)

        btn_register.setOnClickListener{
            (0..tiets.size)
                    .filter { tiets[it].text.toString().isEmpty() }
                    .forEach { tils[it].error="Enter a value" }

            /*if (!haserror) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(this@RegisterActivity) { res ->
                            if (res.isSuccessful){
                                startActivity(Intent(this@RegisterActivity, ProfileSelectionActivity::class.java))
                                finish()
                            }
                            else{
                                Toast.makeText(this@RegisterActivity, "Error, please try again",Toast.LENGTH_SHORT).show()
                            }
                        }
            }*/


        }
    }
}
