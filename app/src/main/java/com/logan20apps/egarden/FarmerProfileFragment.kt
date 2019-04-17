package com.logan20apps.egarden

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by kwasi on 17/02/2018.
 */
class FarmerProfileFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_farmer_profile,container,false)
        view.findViewById<TextInputEditText>(R.id.tiet_fname).setText(User.fname)
        view.findViewById<TextInputEditText>(R.id.tiet_lname).setText(User.lname)
        view.findViewById<TextInputEditText>(R.id.tiet_email).setText(User.email)

        view.findViewById<Button>(R.id.btn_register).setOnClickListener{
            if (view.findViewById<TextInputEditText>(R.id.tiet_oldpassword).text.toString().isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(User.email,view.findViewById<TextInputEditText>(R.id.tiet_oldpassword).text.toString())
                        .addOnCompleteListener{
                            if (it.isSuccessful){
                                val user = FirebaseAuth.getInstance().currentUser
                                FirebaseDatabase.getInstance().reference.child("users").child(user?.uid!!).child("firstname").setValue(view.findViewById<TextInputEditText>(R.id.tiet_fname).text.toString())
                                FirebaseDatabase.getInstance().reference.child("users").child(user?.uid!!).child("lastname").setValue(view.findViewById<TextInputEditText>(R.id.tiet_lname).text.toString())
                                FirebaseDatabase.getInstance().reference.child("users").child(user?.uid!!).child("email").setValue(view.findViewById<TextInputEditText>(R.id.tiet_email).text.toString())
                                FirebaseDatabase.getInstance().reference.child("users").child(user?.uid!!).child("displayname").setValue(view.findViewById<TextInputEditText>(R.id.tiet_fname).text.toString() +" "+view.findViewById<TextInputEditText>(R.id.tiet_lname).text.toString())
                                Toast.makeText(context,"Change successful!",Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(context,"An error has occured!",Toast.LENGTH_SHORT).show()
                            }
                        }
            }
            else{
                Toast.makeText(context,"No password entered!",Toast.LENGTH_SHORT).show()
            }

        }
        return view;
    }


}