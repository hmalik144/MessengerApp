package com.example.h_mal.messengerapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.h_mal.messengerapp.data.repository.Repository
import com.tinder.scarlet.Stream
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch


@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.observeText().consumeEach {
                Log.i("WebsocketOut", it)
            }
        }
    }

    fun submitMessage(message: String){
        repository.submitMessage(message)
    }
}