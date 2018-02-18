package com.logan20apps.egarden

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

/**
 * Created by kwasi on 17/02/2018.
 */

class StockImageChooserAdapter(var context:Context, private var v:View, private var stockImageChooser: StockImageChooser) :BaseAdapter(){
    val arr = arrayOf(R.drawable.apple,R.drawable.banana,R.drawable.brocolliicon,R.drawable.carroticon,R.drawable.chivesicon,R.drawable.cucumbericon,
            R.drawable.garlicicon,R.drawable.lettuceicon,R.drawable.okra,R.drawable.onionicon,R.drawable.pepper,R.drawable.potatoicon,R.drawable.sweetpeppericon,
            R.drawable.tomatoicon,R.drawable.watermelon)

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_chosepic,p2,false)
        }
        view!!.findViewById<ImageView>(R.id.iv_pic).setImageResource(arr[p0])
        view.findViewById<ImageView>(R.id.iv_pic).setOnClickListener{
            (v as ImageView).background=(BitmapDrawable(context.resources,BitmapFactory.decodeResource(context.resources,arr[p0])))
            stockImageChooser.dismiss()
        }

        return view
    }

    override fun getItem(p0: Int): Any {
        return -1
    }

    override fun getItemId(p0: Int): Long {
        return -1
    }

    override fun getCount(): Int {
        return arr.size
    }

}