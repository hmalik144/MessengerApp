package com.example.h_mal.messengerapp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MessageItem(
    @SerializedName("Message")
    @Expose
    var message: String? = null,
    @SerializedName("IsSender")
    @Expose
    var isSender: Boolean? = null,
    @SerializedName("TimeStamp")
    @Expose
    var timeStamp: Long = System.currentTimeMillis()
)