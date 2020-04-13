package com.example.shop49k.model.offer

import android.os.Parcel
import android.os.Parcelable

class AddressDetail (
    val id : String,
    val city_id : String,
    val city_name : String,
    val district_id : String,
    val district_name : String,
    val commune_id : String,
    val commune_name : String,
    val type : String,
    val external_address : String,
    val user_id : String,
    val offer_id : String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(city_id)
        parcel.writeString(city_name)
        parcel.writeString(district_id)
        parcel.writeString(district_name)
        parcel.writeString(commune_id)
        parcel.writeString(commune_name)
        parcel.writeString(type)
        parcel.writeString(external_address)
        parcel.writeString(user_id)
        parcel.writeString(offer_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddressDetail> {
        override fun createFromParcel(parcel: Parcel): AddressDetail {
            return AddressDetail(parcel)
        }

        override fun newArray(size: Int): Array<AddressDetail?> {
            return arrayOfNulls(size)
        }
    }
}