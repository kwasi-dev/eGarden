package com.logan20apps.testrun

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_register.setOnClickListener{
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }
        btn_login.setOnClickListener {
            til_email.error=null
            til_password.error=null

            val email = tiet_email.text.toString()
            val password = tiet_password.text.toString()

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                til_email.error = "Please enter a valid email."
                tiet_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                til_password.error = "Please enter a password."
                tiet_password.requestFocus()
                return@setOnClickListener
            }

            authenticate(email,password)

        }
    }

    private fun authenticate(email: String = "", password: String = ""){
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { p0 ->
                    if(p0.isSuccessful){
                        gotoNext()
                    }
                    else{
                        AlertDialog.Builder(this@LoginActivity)
                                .setTitle("Error")
                                .setMessage("The email or password you have entered is invalid. Please try again")
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok,null)
                                .show()
                    }
                }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser!=null){
            gotoNext()
        }
    }

    private fun gotoNext() {
        //get user type from firebase db and set accordingly

    }

}
