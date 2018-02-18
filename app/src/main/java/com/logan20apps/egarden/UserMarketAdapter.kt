package com.logan20apps.egarden

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by kwasi on 18/02/2018.
 */
class UserMarketAdapter(val context:Activity):BaseAdapter() {
    var items = ArrayList<FarmerInventoryItem>()
    init {
        for(x in Market.list){
            items.add(x)
        }
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1;
        val item = getItem(p0)

        if (view==null){
            view =LayoutInflater.from(context).inflate(R.layout.item_single_market,p2,false)
        }

        view!!.findViewById<TextView>(R.id.tv_cost).setText(String.format("$%.2f",item.price))
        val decodedString = Base64.decode(item.pictureStr, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        view.findViewById<ImageView>(R.id.iv_picture).background=(BitmapDrawable(context.resources,decodedByte))
        view.findViewById<TextView>(R.id.tv_title).text=item.itemName

        view.setOnClickListener{
            val ip = ItemPreview()
            ip.item=getItem(p0)
            ip.show(context.fragmentManager,"TAG" )
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