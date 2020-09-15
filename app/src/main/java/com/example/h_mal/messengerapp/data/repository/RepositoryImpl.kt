package com.example.h_mal.messengerapp.data.repository

import android.util.Log
import com.example.h_mal.messengerapp.data.network.MessengerApi
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import java.io.IOException

class RepositoryImpl(
    private val messengerApi: MessengerApi
) : Repository{

    override fun observeText(): ReceiveChannel<String> {
        return messengerApi.observerMessage()
    }

    override fun submitMessage(message: String){
        val send = messengerApi.send(message)
        if (!send){
            throw IOException("Could not send message")
        }
    }

    override fun getEvent(): ReceiveChannel<WebSocket.Event.OnConnectionFailed> {
        return messengerApi.observerFailure()
    }

}