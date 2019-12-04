package com.example.userlist.data.api.model

import android.os.Parcel
import android.os.Parcelable

data class Location (
    val street: Street?,
    val city: String?,
    val state: String?,
    val postcode: String?,
    val coordinates: Coordinates?,
    val timezone: Timezone?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Street::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Coordinates::class.java.classLoader),
        parcel.readParcelable(Timezone::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(street, flags)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(postcode)
        parcel.writeParcelable(coordinates, flags)
        parcel.writeParcelable(timezone, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }

}