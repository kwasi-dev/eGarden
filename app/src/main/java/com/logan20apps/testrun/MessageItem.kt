package com.logan20apps.testrun

import org.json.JSONObject

/**
 * Created by kwasi on 17/02/2018.
 */
class  MessageItem(val rec:String, val sender:String, val timeSent:String, val message:String){
    companion object {
        fun getObjFromJson(str:String):MessageItem{
            val json = JSONObject(str)
            return MessageItem(json.getString("rec"),json.getString("sender"),json.getString("timeSent"),json.getString("message"))

        }
    }
    fun toJson(): JSONObject {
        val obj = JSONObject()
        obj.put("rec",rec)
        obj.put("sender",sender)
        obj.put("timeSent",timeSent)
        obj.put("message",message)
        return obj
    }
}