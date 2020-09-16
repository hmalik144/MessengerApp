package com.example.h_mal.messengerapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.h_mal.messengerapp.data.network.model.MessageItem
import com.example.h_mal.messengerapp.data.repository.Repository
import com.example.h_mal.messengerapp.utils.Event
import com.example.h_mal.messengerapp.utils.convertDateEpoch
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import java.io.IOException
import java.util.*
import kotlin.concurrent.schedule
import kotlin.random.Random


@ExperimentalCoroutinesApi
class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    // get live data from the database
    val messagesLiveData = repository.getMessagesLiveData()
    // Event live data to push error messages to the UI
    val operationFailure = MutableLiveData<Event<String>>()

    init {
        viewModelScope.launch {
            repository.observeWebsocketMessage().consumeEach {
                // add message to room database
                addMessageItemToDatabase(it)
            }
        }
        viewModelScope.launch {
            repository.observeWebsocketEvent().consumeEach {
                when(it){
                    is WebSocket.Event.OnConnectionOpened<*> ->{
                        saveDateStamp()
                    }
                }
                // Can handle different websocket events here
            }
        }
    }

    fun submitMessage(message: String){
        if (message.isBlank()) return

        try {
            // Submit my message
            repository.submitMessageToApi(message.trim(), true)
            // Submit a random reply message with a delay of a second
            Timer().schedule(1000){
                repository.submitMessageToApi(randomString(), false)
            }
        }catch (exception: IOException){
            val failureMessage = exception.message ?: "Failed to send"
            operationFailure.postValue(Event(failureMessage))
        }
    }

    private fun addMessageItemToDatabase(
        messageItem: MessageItem
    ){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addMessageItemToDatabase(messageItem)
        }
    }

    private fun saveDateStamp(){
        CoroutineScope(Dispatchers.IO).launch {
            // get last entry from room database
            val lastEntry = repository.getLastEntry() ?: return@launch
            // if its greater than 2 hours
            if (lastEntry.isGreaterThanTwoHours()){
                lastEntry.timeStamp.convertDateEpoch()?.let {
                    repository.addTimeStamp(it)
                }
            }
        }
    }

    // Creates a random string of 15 characters to simulate a reply
    private fun randomString(): String {
        val charPool = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val randomString = (1..15)
            .map { i -> Random.nextInt(0, charPool.length) }
            .map(charPool::get)
            .joinToString("");

        return randomString
    }
}