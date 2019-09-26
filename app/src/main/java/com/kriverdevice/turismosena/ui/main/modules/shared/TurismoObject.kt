package com.kriverdevice.turismosena.ui.main.modules.shared

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

class TurismoObject(
    var id: String? = null,
    var description: String,
    var address: String,
    var email: String,
    var mobile_hone: String,
    var local_phone: String,
    var web: String
){
    constructor(jsonObject: JSONObject):this(
        jsonObject.getString("_id"),
        jsonObject.getString("description"),
        jsonObject.getString("address"),
        jsonObject.getString("email"),
        jsonObject.getString("mobile_hone"),
        jsonObject.getString("local_phone"),
        jsonObject.getString("web"))

    override fun toString(): String {

        return "{\"description\":%s,\"address\":%s,\"email\":%s,\"mobile_hone\":%s,\"local_phone\":%s,\"webPage\":%s}"
            .format(description,address,email,mobile_hone,local_phone,web)
    }

    companion object {
        fun mapArray(jsonArray: JSONArray):ArrayList<TurismoObject>{
            var turismoObjectsArray = ArrayList<TurismoObject>()
            for (i in 1..(jsonArray.length())){
                Log.d("***->CountMap", ""+i)
                turismoObjectsArray.add(TurismoObject( jsonArray.getJSONObject(i-1) ))
            }
            return turismoObjectsArray
        }
    }
}
