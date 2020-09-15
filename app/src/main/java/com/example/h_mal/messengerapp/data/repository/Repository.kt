package com.example.h_mal.messengerapp.data.repository

import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.channels.ReceiveChannel

interface Repository {

    fun observeText(): ReceiveChannel<String>
    fun submitMessage(message: String)
    fun getEvent(): ReceiveChannel<WebSocket.Event>
}