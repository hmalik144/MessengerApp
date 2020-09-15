package com.example.h_mal.messengerapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val sender: Boolean,
    val message: String
)