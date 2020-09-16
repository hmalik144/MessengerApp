package com.example.h_mal.messengerapp.data.repository

import android.util.Log
import com.example.h_mal.messengerapp.data.network.MessengerApi
import com.example.h_mal.messengerapp.data.network.model.MessageItem
import com.example.h_mal.messengerapp.data.room.AppRoomDatabase
import com.example.h_mal.messengerapp.data.room.MessageEntity
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import java.io.IOException

class RepositoryImpl(
    private val messengerApi: MessengerApi,
    private val database: AppRoomDatabase
) : Repository{

    override fun observeWebsocketMessage(): ReceiveChannel<MessageItem> {
        return messengerApi.observerMessage()
    }

    override fun observeWebsocketEvent(): ReceiveChannel<WebSocket.Event> {
        return messengerApi.observerEvent()
    }

    // Send message to websocket or throw error if no connection
    override fun submitMessageToApi(message: String, isSender: Boolean){
        val messageItem = MessageItem(message, isSender)
        val send = messengerApi.send(messageItem)
        if (!send){
            throw IOException("Could not send message")
        }
    }

    override fun getMessagesLiveData() = database.getMessageDao().getAllItems()

    override suspend fun addMessageItemToDatabase(
        messageItem: MessageItem
    ){
        val entity = MessageEntity(messageItem)
        database.getMessageDao().saveSingleItem(entity)
    }

    override suspend fun addTimeStamp(
        timestampMessage: String
    ){
        val entity = MessageEntity(timestampMessage)
        database.getMessageDao().saveSingleItem(entity)
    }

    override suspend fun getLastEntry(): MessageEntity? {
        return database.getMessageDao().getLastEntry()
    }

}