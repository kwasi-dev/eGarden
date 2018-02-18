package com.logan20apps.egarden

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import android.app.Activity
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val tiets = arrayOf(tiet_fname,tiet_lname,tiet_email,tiet_password,tiet_password_confirm)
        val tils = arrayOf(til_fname,til_lname,til_email,til_password,til_password_confirm)

        btn_register.setOnClickListener{
            var haserror=false
            (0 until tils.size).forEach{tils[it].error=null}
            (0 until tiets.size)
                    .filter { tiets[it].text.toString().isEmpty() }
                    .forEach { tils[it].error="Enter a value";haserror=true}
            if (!Patterns.EMAIL_ADDRESS.matcher(tiet_email.text.toString()).matches()){til_email.error="Enter a valid email";haserror=true}
            if (!haserror) {
                startActivityForResult(Intent(this@RegisterActivity, ProfileSelectionActivity::class.java),1000)
            }
            else{
                Toast.makeText(this@RegisterActivity,"Please correct the errors!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {

                val result = data.getStringExtra("result")
                Log.w("ERR",result)
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(tiet_email.text.toString(),tiet_password.text.toString())
                    .addOnCompleteListener(this@RegisterActivity) { res ->
                        if (res.isSuccessful){
                            val user = FirebaseAuth.getInstance().currentUser
                            user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(tiet_fname.text.toString()+" "+tiet_lname.text.toString()).build())
                            val map = HashMap<String, Any>()
                            map["usertype"] = result
                            map["firstname"] = tiet_fname.text.toString()
                            map["lastname"] = tiet_lname.text.toString()
                            map["email"] = tiet_email.text.toString()
                            map["displayname"] = tiet_fname.text.toString()+" "+tiet_lname.text.toString()
                            FirebaseDatabase.getInstance().reference.child("users").child(user?.uid).setValue(map)
                            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this@RegisterActivity, res.exception?.message,Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
