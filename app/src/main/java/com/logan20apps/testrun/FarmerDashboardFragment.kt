package com.logan20apps.testrun

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by kwasi on 17/02/2018.
 */

class FarmerDashboardFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_farmer_dashboard,container,false)
        view.findViewById<TextView>(R.id.tv_farmername).setText(User.fname + " "+ User.lname)
        return view
    }
}