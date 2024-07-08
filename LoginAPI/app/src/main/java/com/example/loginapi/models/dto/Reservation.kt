package com.example.loginapi.models.dto

import android.os.Parcel
import android.os.Parcelable

@Parcelize
data class Reservation(
    val id: Int,
    val user_id: Int,
    val restaurant_id: Int,
    val date: String,
    val time: String,
    val people: Int,
    val status: String,
    val created_at: String,
    val updated_at: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(user_id)
        parcel.writeInt(restaurant_id)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeInt(people)
        parcel.writeString(status)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reservation> {
        override fun createFromParcel(parcel: Parcel): Reservation {
            return Reservation(parcel)
        }

        override fun newArray(size: Int): Array<Reservation?> {
            return arrayOfNulls(size)
        }
    }
}
