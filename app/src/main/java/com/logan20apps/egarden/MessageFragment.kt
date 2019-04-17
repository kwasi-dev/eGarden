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
class MessageFragment :Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_msg_layout,container,false)
        val list = view.findViewById<ListView>(R.id.lv_msgs)
        list.adapter=MsgAdapter(context!!)
        return view
    }
}