package com.example.shop49k.model.offer

import android.os.Parcel
import android.os.Parcelable

data class DataOfferDetail (

	val id : Int,
	val title : String,
	val apply_start_time : String,
	val apply_end_time : String,
	val type : Int,
	val how_to_use : String,
	val apply_condition : String,
	val total : Int,
	val used : Int,
	val is_unlimited : Int,
	val status : Int,
	val created_time : String?,
	val updated_time : String?,
	val user_id : Int,
	val short_code : String?,
	val discount_type : String?,
	val discount_value : Double,
	val discount_note : String?,
	val banner_image : String?,
	val images : List<ImageOffer>,
	val address : List<AddressDetail>
): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readInt(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readInt(),
		parcel.readString(),
		parcel.readString(),
		parcel.readInt(),
		parcel.readInt(),
		parcel.readInt(),
		parcel.readInt(),
		parcel.readString(),
		parcel.readString(),
		parcel.readInt(),
		parcel.readString(),
		parcel.readString(),
		parcel.readDouble(),
		parcel.readString(),
		parcel.readString(),
		parcel.createTypedArrayList(ImageOffer),
		parcel.createTypedArrayList(AddressDetail)
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeInt(id)
		parcel.writeString(title)
		parcel.writeString(apply_start_time)
		parcel.writeString(apply_end_time)
		parcel.writeInt(type)
		parcel.writeString(how_to_use)
		parcel.writeString(apply_condition)
		parcel.writeInt(total)
		parcel.writeInt(used)
		parcel.writeInt(is_unlimited)
		parcel.writeInt(status)
		parcel.writeString(created_time)
		parcel.writeString(updated_time)
		parcel.writeInt(user_id)
		parcel.writeString(short_code)
		parcel.writeString(discount_type)
		parcel.writeDouble(discount_value)
		parcel.writeString(discount_note)
		parcel.writeString(banner_image)
		parcel.writeTypedList(images)
		parcel.writeTypedList(address)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<DataOfferDetail> {
		override fun createFromParcel(parcel: Parcel): DataOfferDetail {
			return DataOfferDetail(parcel)
		}

		override fun newArray(size: Int): Array<DataOfferDetail?> {
			return arrayOfNulls(size)
		}
	}
}