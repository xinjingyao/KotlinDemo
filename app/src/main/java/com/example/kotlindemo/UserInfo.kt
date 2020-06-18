package com.example.kotlindemo

import android.os.Parcel
import android.os.Parcelable

class UserInfo() : Parcelable{

    var email: String? = null
    var icon: String? = null
    var id: Int = 0
    var password: String? = null
    var type: Int = 0
    var username: String? = null
    var collectIds: MutableList<Int>? = null

    constructor(parcel: Parcel) : this() {
        email = parcel.readString()
        icon = parcel.readString()
        id = parcel.readInt()
        password = parcel.readString()
        type = parcel.readInt()
        username = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(icon)
        parcel.writeInt(id)
        parcel.writeString(password)
        parcel.writeInt(type)
        parcel.writeString(username)
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

}