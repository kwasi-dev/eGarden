package com.logan20apps.egarden

import org.json.JSONObject

/**
 * Created by kwasi on 18/02/2018.
 */
class History(val unit:String, val quantity:Int, val itemname:String, val date:String, val pricePaid:Double) {
    companion object {
        fun getObjFromJson(str:String):History{
            val json = JSONObject(str)
            return History(json.getString("unit"),json.getInt("quantity"),json.getString("itemname"),json.getString("date"),json.getDouble("pricePaid"))

        }
    }
    fun toJson(): JSONObject {
        val obj = JSONObject()
        obj.put("unit",unit)
        obj.put("quantity",quantity)
        obj.put("itemname",itemname)
        obj.put("date",date)
        obj.put("pricePaid",pricePaid)
        return obj
    }
}