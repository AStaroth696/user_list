package com.example.userlist.data.api.model

import android.os.Parcel
import android.os.Parcelable

data class DateWrapper(
    val date: String?,
    val age: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DateWrapper> {
        override fun createFromParcel(parcel: Parcel): DateWrapper {
            return DateWrapper(parcel)
        }

        override fun newArray(size: Int): Array<DateWrapper?> {
            return arrayOfNulls(size)
        }
    }

}