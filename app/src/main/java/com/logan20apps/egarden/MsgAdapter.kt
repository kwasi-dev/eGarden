package com.logan20apps.egarden

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by kwasi on 18/02/2018.
 */
class MsgAdapter(var context:Context):BaseAdapter() {
    var items = User.messages
    init{
        items.add(MessageItem("","","","I am interested in some of your goods. Send me a number","Jane Doe"))
        items.add(MessageItem("","","","Do you sell lettuce?","Jack Bane"))
        items.add(MessageItem("","","","I want some shrimp","Fisherman S Friend"))
    }
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_msg_single,p2,false)
        }

        val item = getItem(p0)
        view!!.findViewById<TextView>(R.id.tv_name).setText(item.sendername)
        view.findViewById<TextView>(R.id.tv_body).setText(item.message)
        return view
    }

    override fun getItem(p0: Int): MessageItem {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return -1
    }

    override fun getCount(): Int {
        return items.size
    }
}