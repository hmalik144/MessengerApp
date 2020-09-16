package com.example.h_mal.messengerapp.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun Long.convertDateEpoch(): String? {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
        val date = Date(this)
         sdf.format(date)
    }catch (e: Exception){
        e.printStackTrace()
        null
    }
}