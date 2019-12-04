package com.example.userlist.data.api.model

import android.os.Parcel
import android.os.Parcelable

data class Id(
    val name: String?,
    val value: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Id> {
        override fun createFromParcel(parcel: Parcel): Id {
            return Id(parcel)
        }

        override fun newArray(size: Int): Array<Id?> {
            return arrayOfNulls(size)
        }
    }

}