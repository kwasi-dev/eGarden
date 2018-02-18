package com.logan20apps.testrun

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var dialog :AlertDialog?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dialog=AlertDialog.Builder(this@LoginActivity)
                .setCancelable(false)
                .setView(R.layout.login_prompt)
                .create()

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
        dialog?.show()
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { p0 ->
                    dialog?.cancel()
                    if(p0.isSuccessful){
                        gotoNext(0)
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

    private fun gotoNext(param:Int = 1) {
        if (!dialog?.isShowing!! && param==0){
            dialog?.show()
        }
        FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser?.uid).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                dialog?.cancel()
            }
            override fun onDataChange(p0: DataSnapshot) {
                dialog?.cancel()
                for (msgsnap in p0.children){
                    when(msgsnap.key){
                        "lastname"->{
                            User.lname = msgsnap.value.toString()
                        }
                        "firstname"->{
                            User.fname = msgsnap.value.toString()
                        }
                        "email"->{
                            User.email=msgsnap.value.toString()
                        }
                        "inventory"->{
                            val json = JSONObject(msgsnap.value.toString())
                            val keys = json.keys()
                            User.inventory.clear()
                            for (key in keys){
                                json.getJSONObject(key).put("key",key)
                                User.inventory.add(FarmerInventoryItem.getObjFromJson(json.getJSONObject(key).toString()))
                            }
                        }
                        "usertype"->{
                            User.userType = UserType.valueOf(msgsnap.value.toString())
                        }
                        "messages"->{
                            val json = JSONObject(msgsnap.value.toString())
                            val keys = json.keys()
                            for (key in keys){
                                User.messages.put(FarmerInventoryItem.getObjFromJson(json.getJSONObject(key).toString()))
                            }
                        }
                        "feedback"->{
                            val json = JSONObject(msgsnap.value.toString())
                            val keys = json.keys()
                            for (key in keys){
                                User.feedback.put(FarmerInventoryItem.getObjFromJson(json.getJSONObject(key).toString()))
                            }
                        }
                        "schedule"->{
                            val json = JSONObject(msgsnap.value.toString())
                            val keys = json.keys()
                            for (key in keys){
                                User.schedule.put(FarmerInventoryItem.getObjFromJson(json.getJSONObject(key).toString()))
                            }
                        }
                        "history"->{
                            val json = JSONObject(msgsnap.value.toString())
                            val keys = json.keys()
                            for (key in keys){
                                User.history.put(FarmerInventoryItem.getObjFromJson(json.getJSONObject(key).toString()))
                            }
                        }
                    }
                }
                startActivity(Intent(this@LoginActivity,MainFarmerActivity::class.java))
                finish()
            }
        })

    }

}
