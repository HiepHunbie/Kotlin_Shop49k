package com.example.shop49k.model.delivery

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class DeliveryDetail (
    val title:String,
    val id : String,
    val time : String,
    // 0 : shipping , 1 : shipped, 2 : cancel
    val status : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(id)
        parcel.writeString(time)
        parcel.writeInt(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DeliveryDetail> {
        override fun createFromParcel(parcel: Parcel): DeliveryDetail {
            return DeliveryDetail(parcel)
        }

        override fun newArray(size: Int): Array<DeliveryDetail?> {
            return arrayOfNulls(size)
        }
    }
}