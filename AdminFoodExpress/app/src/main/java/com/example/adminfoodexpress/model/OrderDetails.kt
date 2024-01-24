package com.example.adminfoodexpress.model


import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class OrderDetails() : Serializable{
    var userId: String? = null
    var userName: String? = null
    var foodNames: MutableList<String>? = null
    var foodImages: MutableList<String>? = null
    var foodPrices: MutableList<String>? = null
    var foodQuantities: MutableList<Int>? = null
    var address: String? = null
    var totalPrice: String? = null
    var phoneNumber: String? = null
    var orderAccepted: Boolean = false
    var paymentReceived: Boolean = false
    var itemPushKey: String? = null
    var currentTime: Long = 0

    constructor(parcel: Parcel) : this() {
        userId = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }


//     fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(userId)
//        parcel.writeString(userName)
//        parcel.writeString(address)
//        parcel.writeString(totalPrice)
//        parcel.writeString(phoneNumber)
//        parcel.writeString(orderAccepted.toString())
//        parcel.writeString(paymentReceived.toString())
//        parcel.writeString(itemPushKey)
//        parcel.writeLong(currentTime)
//    }

     fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}