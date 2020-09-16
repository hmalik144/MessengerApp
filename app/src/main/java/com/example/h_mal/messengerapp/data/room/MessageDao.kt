package com.example.h_mal.messengerapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllItems(messages : List<MessageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSingleItem(message : MessageEntity)

    @Query("SELECT * FROM MessageEntity")
    fun getAllItems() : LiveData<List<MessageEntity>>

    @Query("SELECT * FROM MessageEntity WHERE id = :id")
    fun getItem(id: Int) : LiveData<MessageEntity>

    @Query("DELETE FROM MessageEntity")
    suspend fun deleteAllEntries()

    @Delete
    fun deleteSingleEntry(message: MessageEntity)

    @Query("SELECT * FROM MessageEntity ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastEntry(): MessageEntity?
}