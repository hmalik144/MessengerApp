package com.example.h_mal.messengerapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.h_mal.messengerapp.R
import com.example.h_mal.messengerapp.data.room.MessageEntity
import com.example.h_mal.messengerapp.utils.setColour
import kotlinx.android.synthetic.main.simple_message_layout.view.*
import kotlinx.android.synthetic.main.timestamp_item_layout.view.*

const val SENT_CONST = 101
const val RECEIVED_CONST = 102
const val TIMESTAMP_CONST = 103
class MessageRecyclerAdapter(
    messageLiveData: LiveData<List<MessageEntity>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var messages: List<MessageEntity>? = null

    init {
        // Observer live data and update list on change
        messageLiveData.observeForever{
            messages = it
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when(viewType){
            SENT_CONST ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.message_right, parent, false)
                CustomViewHolder(view)
            }
            RECEIVED_CONST ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.message_left, parent, false)
                CustomViewHolder(view)
            }
            TIMESTAMP_CONST ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.timestamp_item_layout, parent, false)
                TimestampViewHolder(view)
            }
            else -> EmptyViewHolder(parent.context)
        }

    }

    override fun getItemCount(): Int {
        return messages?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is CustomViewHolder ->{
                messages?.get(position)?.let {
                    holder.populateMessage(it)
                }
            }
            is TimestampViewHolder ->{
                messages?.get(position)?.let {
                    holder.populateMessage(it)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        messages?.get(position)?.isSender?.let {
            return when(it){
                true -> SENT_CONST
                false -> RECEIVED_CONST
            }
        }
        return TIMESTAMP_CONST
    }

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val messageTv: TextView = itemView.inner_text
        private val card: CardView = itemView.card

        fun populateMessage(messageEntity: MessageEntity){
            messageTv.text = messageEntity.message
            if (messageEntity.isSender!!){
                card.setColour(R.color.senderColour)
            }else{
                card.setColour(R.color.receiverColour)
            }
        }
    }

    class TimestampViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val timestampTV: TextView = itemView.title

        fun populateMessage(messageEntity: MessageEntity){
            timestampTV.text = messageEntity.message
        }
    }

    class EmptyViewHolder(context: Context): RecyclerView.ViewHolder(View(context))
}