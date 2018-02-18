package com.logan20apps.testrun

import org.json.JSONArray

/**
 * Created by kwasi on 17/02/2018.
 */
class User {
    companion object {
        var inventory = ArrayList<FarmerInventoryItem>()
        var fname =""
        var lname =""
        var email =""
        var userType : UserType?=null
        var messages = JSONArray()
        var feedback = JSONArray()
        var schedule = JSONArray()
        var history = JSONArray()
    }
}