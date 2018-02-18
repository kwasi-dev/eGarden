package com.logan20apps.egarden

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.GridView

/**
 * Created by kwasi on 18/02/2018.
 */
class UserMarketFragment :Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_market,container,false)
        val gv = view.findViewById<GridView>(R.id.gv_market)
        gv.adapter = UserMarketAdapter(activity)
        return view
    }
}