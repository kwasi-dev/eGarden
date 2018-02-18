package com.logan20apps.egarden

import android.app.DialogFragment
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

/**
 * Created by kwasi on 18/02/2018.
 */
class ItemPreview :DialogFragment() {
    lateinit var item: FarmerInventoryItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var v = inflater.inflate(R.layout.item_preview,container,false)

        FirebaseDatabase.getInstance().reference.child("users").child(item.userid).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (msgsnap in p0.children){
                    when(msgsnap.key){
                        "displayname"->{
                            v.findViewById<TextView>(R.id.tv_name).text=msgsnap.value.toString()
                        }
                        "rating"->{
                            v.findViewById<RatingBar>(R.id.rb_rating).rating=msgsnap.value.toString().toFloat()
                        }

                    }
                }
            }

        })

        v.findViewById<TextView>(R.id.tv_itemname).text=item.itemName
        v.findViewById<TextView>(R.id.tv_quantityavail).text=item.quantity.toString() + item.unit + " Available"
        v.findViewById<TextView>(R.id.tv_rate).text=String.format("$%.2f",item.price)+ " per"+ item.unit
        v.findViewById<TextInputEditText>(R.id.tiet_quantity).addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isEmpty()){
                    v.findViewById<TextInputEditText>(R.id.tiet_total).setText("")
                }
                else{
                    v.findViewById<TextInputEditText>(R.id.tiet_total).setText(String.format("$%.2f",(item.price*p0.toString().toDouble())))
                }
            }

        })


        val decodedString = Base64.decode(item.pictureStr, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        v.findViewById<ImageView>(R.id.iv_pic2).background=(BitmapDrawable(activity.resources,decodedByte))


        v.findViewById<Button>(R.id.btn_buynow).setOnClickListener {
            val hist = History(item.unit,v.findViewById<TextInputEditText>(R.id.tiet_quantity).text.toString().toInt(),item.itemName, SimpleDateFormat("yyyy-MM-dd").format(Date()),v.findViewById<TextInputEditText>(R.id.tiet_total).text.toString().replace('$',' ').toDouble())
            FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser?.uid).child("history").push().setValue(hist.toJson().toString())
            this@ItemPreview.dismiss()
            Toast.makeText(activity,"Purchase completed successfully",Toast.LENGTH_SHORT).show()
        }
        return v;
    }


}