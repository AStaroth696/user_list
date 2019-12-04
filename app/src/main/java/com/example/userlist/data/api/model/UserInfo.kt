package com.example.userlist.data.api.model

import android.os.Parcel
import android.os.Parcelable
import com.example.userlist.view.SectionedLayout

data class UserInfo(
    val gender: String?,
    val name: Name?,
    val location: Location?,
    val email: String?,
    val login: Login?,
    val dob: DateWrapper?,
    val registered: DateWrapper?,
    val phone: String?,
    val cell: String?,
    val id: Id?,
    val picture: Picture?,
    val nat: String?
    ) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Name::class.java.classLoader),
        parcel.readParcelable(Location::class.java.classLoader),
        parcel.readString(),
        parcel.readParcelable(Login::class.java.classLoader),
        parcel.readParcelable(DateWrapper::class.java.classLoader),
        parcel.readParcelable(DateWrapper::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Id::class.java.classLoader),
        parcel.readParcelable(Picture::class.java.classLoader),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(gender)
        parcel.writeParcelable(name, flags)
        parcel.writeParcelable(location, flags)
        parcel.writeString(email)
        parcel.writeParcelable(login, flags)
        parcel.writeParcelable(dob, flags)
        parcel.writeParcelable(registered, flags)
        parcel.writeString(phone)
        parcel.writeString(cell)
        parcel.writeParcelable(id, flags)
        parcel.writeParcelable(picture, flags)
        parcel.writeString(nat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfo> {
        override fun createFromParcel(parcel: Parcel): UserInfo {
            return UserInfo(parcel)
        }

        override fun newArray(size: Int): Array<UserInfo?> {
            return arrayOfNulls(size)
        }
    }

    fun toSections(): List<SectionedLayout.Section> {
        val locationSection = SectionedLayout.Section("Location",
            listOf(
                "street" to "${location?.street?.number} ${location?.street?.name}",
                "state" to location?.state,
                "city" to location?.city,
                "coordinates" to "${location?.coordinates?.latitude} : ${location?.coordinates?.longitude}"
            ))
        val registrationSection = SectionedLayout.Section("Registration",
            listOf(
                "date" to registered?.date,
                "age" to registered?.age
            ))
        val idSection = SectionedLayout.Section("Id",
            listOf(
                "name" to id?.name,
                "value" to id?.value
            ))
        val loginSection = SectionedLayout.Section("Login",
            listOf(
                "uuid" to login?.uuid,
                "username" to login?.username,
                "password" to login?.password,
                "salt" to login?.salt,
                "md5" to login?.md5,
                "sha1" to login?.sha1,
                "sha256" to login?.sha256
            ))
        return listOf(
            locationSection,
            registrationSection,
            idSection,
            loginSection
        )
    }

}