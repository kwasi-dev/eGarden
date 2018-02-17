package com.logan20apps.testrun

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView

/**
 * Created by kwasi on 17/02/2018.
 */
class FarmerInventoryFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_farmer_inventory,container,false)
        val list = view.findViewById<ListView>(R.id.lv_farmerinventory)
        list.adapter=FarmerInventoryAdapter(context)
        view.findViewById<ImageButton>(R.id.ib_addinventory).setOnClickListener{
            fragmentManager.beginTransaction().replace(R.id.fl_content,FarmerAddInventory()).commit()
        }
        return view
    }
}