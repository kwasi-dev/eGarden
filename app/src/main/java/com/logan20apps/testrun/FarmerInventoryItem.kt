package com.logan20apps.testrun

import org.json.JSONObject

/**
 * Created by kwasi on 17/02/2018.
 */
class FarmerInventoryItem(var itemName:String, var quantity:Int, var unit:String, var plantedDate:String, var harvestedDate:String, var price:Double, var pictureStr:String){
    companion object {
        fun getObjFromJson(str:String):FarmerInventoryItem{
            val json = JSONObject(str)
            return FarmerInventoryItem(json.getString("itemname"),json.getInt("quantity"),json.getString("unit"),json.getString("plantedDate"),json.getString("harvestedDate"),json.getDouble("price"),json.getString("pictureStr"))

        }
    }
    fun toJson():JSONObject{
        val obj = JSONObject()
        obj.put("itemname",itemName)
        obj.put("quantity",quantity)
        obj.put("unit",unit)
        obj.put("plantedDate",plantedDate)
        obj.put("harvestedDate",harvestedDate)
        obj.put("price",price)
        obj.put("pictureStr",pictureStr)
        return obj
    }
}