package com.example.userlist.data.api.model

import android.os.Parcel
import android.os.Parcelable

data class Login(
    val uuid: String?,
    val username: String?,
    val password: String?,
    val salt: String?,
    val md5: String?,
    val sha1: String?,
    val sha256: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(salt)
        parcel.writeString(md5)
        parcel.writeString(sha1)
        parcel.writeString(sha256)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Login> {
        override fun createFromParcel(parcel: Parcel): Login {
            return Login(parcel)
        }

        override fun newArray(size: Int): Array<Login?> {
            return arrayOfNulls(size)
        }
    }

}