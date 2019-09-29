package com.kriverdevice.turismosena.ui.main.shared

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONArray
import org.json.JSONObject


class TurismoObject : Parcelable {

    var id: String? = null
    lateinit var description: String
    lateinit var address: String
    lateinit var email: String
    lateinit var mobile_hone: String
    lateinit var local_phone: String
    lateinit var web: String

    // Necesario para el parcelable
    constructor()

    // Necesario para el Map de JSON
    constructor(
        id: String? = null,
        description: String,
        address: String,
        email: String,
        mobile_hone: String,
        local_phone: String,
        web: String
    ) {
        this.id = ""
        this.description = ""
        this.address = ""
        this.email = ""
        this.mobile_hone = ""
        this.local_phone = ""
        this.web = ""
    }

    // Map del JSON
    constructor(jsonObject: JSONObject) {
        this.id = jsonObject.getString("_id")
        this.description = jsonObject.getString("description")
        this.address = jsonObject.getString("address")
        this.email = jsonObject.getString("email")
        this.mobile_hone = jsonObject.getString("mobile_hone")
        this.local_phone = jsonObject.getString("local_phone")
        this.web = jsonObject.getString("web")
    }

    constructor(parcel: Parcel) : this() {
        // Ya debe tener un ID
        this.id = parcel.readString()
        this.description = parcel.readString()
        this.address = parcel.readString()
        this.email = parcel.readString()
        this.mobile_hone = parcel.readString()
        this.local_phone = parcel.readString()
        this.web = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(description)
        parcel.writeString(address)
        parcel.writeString(email)
        parcel.writeString(mobile_hone)
        parcel.writeString(local_phone)
        parcel.writeString(web)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TurismoObject> {
        override fun createFromParcel(parcel: Parcel): TurismoObject {
            return TurismoObject(parcel)
        }

        override fun newArray(size: Int): Array<TurismoObject?> {
            return arrayOfNulls(size)
        }

        fun mapArray(jsonArray: JSONArray): ArrayList<TurismoObject> {
            var turismoObjectsArray = ArrayList<TurismoObject>()
            for (i in 1..(jsonArray.length())) {
                turismoObjectsArray.add(TurismoObject(jsonArray.getJSONObject(i - 1)))
            }
            return turismoObjectsArray
        }
    }

    override fun toString(): String {
        return "{\"description\":%s,\"address\":%s,\"email\":%s,\"mobile_hone\":%s,\"local_phone\":%s,\"webPage\":%s}"
            .format(description, address, email, mobile_hone, local_phone, web)
    }

}
