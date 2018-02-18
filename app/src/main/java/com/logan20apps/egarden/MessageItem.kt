package com.logan20apps.egarden

import org.json.JSONObject

/**
 * Created by kwasi on 17/02/2018.
 */
class  MessageItem(val rec:String, val sender:String, val timeSent:String, val message:String, val sendername:String){
    companion object {
        fun getObjFromJson(str:String):MessageItem{
            val json = JSONObject(str)
            return MessageItem(json.getString("rec"),json.getString("sender"),json.getString("timeSent"),json.getString("message"),User.fname+" "+User.lname)

        }
    }
    fun toJson(): JSONObject {
        val obj = JSONObject()
        obj.put("rec",rec)
        obj.put("sender",sender)
        obj.put("timeSent",timeSent)
        obj.put("message",message)
        obj.put("sendername",User.fname+" "+User.lname)
        return obj
    }
}
