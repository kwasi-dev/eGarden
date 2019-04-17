package com.logan20apps.egarden

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream

/**
 * Created by kwasi on 17/02/2018.
 */
class FarmerAddInventory :Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_farmer_add_inventory,container,false)
        val tiets = arrayOf(view.findViewById<TextInputEditText>(R.id.tiet_itemname),view.findViewById<TextInputEditText>(R.id.tiet_itemamt),view.findViewById<TextInputEditText>(R.id.tiet_itemcost),view.findViewById<TextInputEditText>(R.id.tiet_harvestdate))
        val tils = arrayOf(view.findViewById<TextInputLayout>(R.id.til_itemname),view.findViewById<TextInputLayout>(R.id.til_itemamt),view.findViewById<TextInputLayout>(R.id.til_itemcost),view.findViewById<TextInputLayout>(R.id.til_harvestdate))

        view.findViewById<ImageView>(R.id.selectpicture).setOnClickListener{
            val sit = StockImageChooser()
            sit.v = it
            sit.show(fragmentManager,"TAG")
        }

        view.findViewById<Button>(R.id.btn_saveinventory).setOnClickListener{
            var haserror=false;

            for (x in tils) x.error=null;

            (0 until tiets.size)
                    .filter { tiets[it].text.toString().isEmpty() }
                    .forEach { tils[it].error="Please enter data";haserror=true }

            if (haserror) return@setOnClickListener;

            val name = tiets[0].text.toString()
            val amount  = tiets[1].text.toString()
            val cost = tiets[2].text.toString()
            val harvestDay = tiets[3].text.toString()
            val unit = view.findViewById<Spinner>(R.id.spnr_unit).selectedItem.toString()
            val baos = ByteArrayOutputStream()
            (view.findViewById<ImageView>(R.id.selectpicture).background as BitmapDrawable).bitmap.compress(Bitmap.CompressFormat.PNG,70,baos)
            val bytearr = baos.toByteArray()
            val image = Base64.encodeToString(bytearr,Base64.DEFAULT)

            val item = FarmerInventoryItem(name,amount.toInt(),unit,"",harvestDay,cost.toDouble(),image,false, FirebaseAuth.getInstance().currentUser!!.uid,"")

            val user = FirebaseAuth.getInstance().currentUser
            FirebaseDatabase.getInstance().reference.child("users").child(user?.uid!!).child("inventory").push().setValue(item.toJson().toString()).addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(context,"Successfully added to inventory",Toast.LENGTH_SHORT).show()
                    (activity as MainFarmerActivity).dashboard()
                }
                else{
                    Toast.makeText(context,"An error has occurred",Toast.LENGTH_SHORT).show()
                }
            }


        }
        return view
    }
}