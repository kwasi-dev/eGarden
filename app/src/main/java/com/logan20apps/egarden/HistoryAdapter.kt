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
class HistoryAdapter(val context:Context):BaseAdapter(){
    val items : ArrayList<History>
    init {
        items = User.history
        notifyDataSetChanged()
    }
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_history,p2,false)
        }
        val item = getItem(p0)
        view!!.findViewById<TextView>(R.id.tv_unit).text=item.unit
        view.findViewById<TextView>(R.id.tv_quantity).text=item.quantity.toString()
        view.findViewById<TextView>(R.id.tv_itemname).text=item.itemname
        view.findViewById<TextView>(R.id.tv_date).text=item.date
        view.findViewById<TextView>(R.id.tv_price).text=String.format("$%.2f",item.pricePaid)
        return view
    }

    override fun getItem(p0: Int): History{
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return -1
    }

    override fun getCount(): Int {
        return items.size
    }

}