package com.example.h_mal.messengerapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.h_mal.messengerapp.data.network.model.MessageItem

const val TWO_HOURS = 20 * 1000
@Entity
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var isSender: Boolean? = null,
    val message: String?,
    val timeStamp: Long = System.currentTimeMillis()
){

    constructor(timeStampMessage: String): this(
        0, null, timeStampMessage
    )

    constructor(messageItem: MessageItem): this(
        0,
        messageItem.isSender,
        messageItem.message,
        messageItem.timeStamp
    )

    fun isGreaterThanTwoHours(): Boolean{
        val current = System.currentTimeMillis()
        val time = current - timeStamp
        return time > TWO_HOURS
    }
}