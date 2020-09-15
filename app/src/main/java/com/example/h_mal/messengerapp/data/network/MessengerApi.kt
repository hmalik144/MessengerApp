package com.example.h_mal.messengerapp.data.network

import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.Stream
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import kotlinx.coroutines.channels.ReceiveChannel
import okhttp3.OkHttpClient


interface MessengerApi {

    @Receive
    fun observerMessage(): ReceiveChannel<String>

    @Receive
    fun observerFailure(): ReceiveChannel<WebSocket.Event.OnConnectionFailed>

    @Send
    fun send(message: Any): Boolean


    // invoke method creating an invocation of the api call
    companion object{
        operator fun invoke(
            okkHttpClient: OkHttpClient
        ) : MessengerApi {

            val webSocketUrl = "ws://echo.websocket.org/"

            // creation of retrofit class
            return Scarlet.Builder()
                .webSocketFactory(okkHttpClient.newWebSocketFactory(webSocketUrl))
                .addMessageAdapterFactory(GsonMessageAdapter.Factory())
                .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
                .build()
                .create()

        }
    }
}