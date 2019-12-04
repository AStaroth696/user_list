package com.example.userlist.data.api.model

import android.os.Parcel
import android.os.Parcelable

data class Picture (
    val large: String?,
    val medium: String?,
    val thumbnail: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(large)
        parcel.writeString(medium)
        parcel.writeString(thumbnail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Picture> {
        override fun createFromParcel(parcel: Parcel): Picture {
            return Picture(parcel)
        }

        override fun newArray(size: Int): Array<Picture?> {
            return arrayOfNulls(size)
        }
    }

}