package com.logan20apps.egarden

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

/**
 * Created by kwasi on 18/02/2018.
 */
class HistoryFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_history, container, false)
        val lv = v.findViewById<ListView>(R.id.lv_history)
        lv.adapter = HistoryAdapter(context)
        return v
    }
}