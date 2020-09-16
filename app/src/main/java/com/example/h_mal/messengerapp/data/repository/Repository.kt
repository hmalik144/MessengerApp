package com.example.h_mal.messengerapp.data.repository

import androidx.lifecycle.LiveData
import com.example.h_mal.messengerapp.data.network.model.MessageItem
import com.example.h_mal.messengerapp.data.room.MessageEntity
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.channels.ReceiveChannel

interface Repository {


    fun observeWebsocketMessage(): ReceiveChannel<MessageItem>
    fun observeWebsocketEvent(): ReceiveChannel<WebSocket.Event>
    fun submitMessageToApi(message: String, isSender: Boolean)
    fun getMessagesLiveData(): LiveData<List<MessageEntity>>
    suspend fun addMessageItemToDatabase(messageItem: MessageItem)
    suspend fun addTimeStamp(timestampMessage: String)
    suspend fun getLastEntry(): MessageEntity?
}