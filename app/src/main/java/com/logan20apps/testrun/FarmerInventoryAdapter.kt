package com.logan20apps.testrun

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONObject
import org.w3c.dom.Text
import android.graphics.Bitmap



/**
 * Created by kwasi on 17/02/2018.
 */
class FarmerInventoryAdapter(val context: Context) : BaseAdapter() {
    var items : ArrayList<FarmerInventoryItem> = User.inventory

    init {
        items = User.inventory
        notifyDataSetChanged()
    }
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1
        if (view==null){
            view=LayoutInflater.from(context).inflate(R.layout.item_farmer_inventory,p2,false)
        }

        val item = getItem(p0)
        val decodedString = Base64.decode(item.pictureStr, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        view!!.findViewById<ImageView>(R.id.iv_image).background=(BitmapDrawable(context.resources,decodedByte))
        view.findViewById<TextView>(R.id.tv_name).text=item.itemName
        view.findViewById<TextView>(R.id.tv_quantity).text=item.quantity.toString()
        view.findViewById<TextView>(R.id.tv_unit).text=item.unit
        view.findViewById<TextView>(R.id.tv_harvestdate).text=String.format("Harvested on: "+item.harvestedDate)
        view.findViewById<TextView>(R.id.tv_price).text=String.format("$%.2f",item.price)
        if(item.isInMarket){
            view.findViewById<ImageView>(R.id.iv_inMarket).visibility=View.VISIBLE
        } else {
            view.findViewById<ImageView>(R.id.iv_inMarket).visibility=View.INVISIBLE
        }

        return view
    }

    override fun getItem(p0: Int): FarmerInventoryItem {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return -1;
    }

    override fun getCount(): Int {
        return items.size
    }
}