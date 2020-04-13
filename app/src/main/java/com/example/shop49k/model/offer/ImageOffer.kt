package com.example.shop49k.model.offer

import android.os.Parcel
import android.os.Parcelable

class ImageOffer (
    val id : String,
    val url : String,
    val name : String,
    val original_name : String?,
    val user_id : String?,
    val type : String?,
    val offer_id : String?,
    val is_featured : String?
): Parcelable {
    constructor(parcel: Parcel) : this(
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
        parcel.writeString(url)
        parcel.writeString(name)
        parcel.writeString(original_name)
        parcel.writeString(user_id)
        parcel.writeString(type)
        parcel.writeString(offer_id)
        parcel.writeString(is_featured)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageOffer> {
        override fun createFromParcel(parcel: Parcel): ImageOffer {
            return ImageOffer(parcel)
        }

        override fun newArray(size: Int): Array<ImageOffer?> {
            return arrayOfNulls(size)
        }
    }
}