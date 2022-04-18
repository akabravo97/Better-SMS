package com.abhijeet.bettermessaging.view.messages

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abhijeet.bettermessaging.databinding.ItemMessageContainerBinding
import com.abhijeet.bettermessaging.model.Message
import com.abhijeet.bettermessaging.utils.findOtpLocation
import com.abhijeet.bettermessaging.utils.formatTime
import kotlinx.android.synthetic.main.item_message_container.view.*

class MessagePhotoAdapter : PagingDataAdapter<Message, MessagePhotoAdapter.MessageViewHolder>(
    MessageComparator
) {

    class MessageViewHolder(private val binding: ItemMessageContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            binding.apply {
                messageContent.text = message.body
                messageSenderName.text = message.sender
                messageDate.text = message.time.formatTime()
                if (message.isOtp) {
                    val pair = message.body.findOtpLocation()
                    val spannable = SpannableString(message.body)
                    spannable.setSpan(
                        ForegroundColorSpan(Color.BLUE),
                        pair.first,
                        pair.second + 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    messageContent.setText(spannable, TextView.BufferType.SPANNABLE)
                }
            }
        }
    }

    companion object {
        private val MessageComparator = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.time == newItem.time
            }

            override fun areContentsTheSame(
                oldItem: Message,
                newItem: Message
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.itemView.setOnClickListener {
                val action = MessageListFragmentDirections.actionGlobalMessageDetailFragment(
                    content = currentItem.body,
                    sender = currentItem.sender,
                    date = currentItem.time.formatTime()
                )
                val bundle = Bundle()
                bundle.putString("sender", currentItem.sender)
                bundle.putString("content", currentItem.body)
                bundle.putString("time", currentItem.time.formatTime())
                holder.itemView.findNavController().navigate(action)
            }
            if (currentItem.isOtp) {
                holder.itemView.message_content.setTextColor(Color.GREEN)

            }
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {
        val binding =
            ItemMessageContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }
}