package com.logan20apps.egarden

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
        var messages = ArrayList<MessageItem>()
        var feedback = JSONArray()
        var schedule = JSONArray()
        var history = ArrayList<History>()
    }
}