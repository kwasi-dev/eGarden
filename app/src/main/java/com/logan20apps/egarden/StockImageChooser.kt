package com.logan20apps.egarden

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView

/**
 * Created by kwasi on 17/02/2018.
 */
class StockImageChooser: DialogFragment(){
    var v : View?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.dialogfragment_pic_chooser,container,false)
        val gv = view.findViewById<GridView>(R.id.gv_pics)
        gv.adapter=StockImageChooserAdapter(context!!, this.v!!, this)
        return view;
    }

}