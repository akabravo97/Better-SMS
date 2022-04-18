package com.abhijeet.bettermessaging.repository

import android.content.Context
import android.provider.Telephony
import com.abhijeet.bettermessaging.model.Message
import com.abhijeet.bettermessaging.utils.containsOtp
import javax.inject.Singleton

@Singleton
class MessageRepository constructor(val context: Context) {
    fun getAllMessages(max: Int, skip: Int): List<Message> {
        var allMessages = mutableListOf<Message>()

        val pointer = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(
                Telephony.Sms.Inbox.ADDRESS,
                Telephony.Sms.Inbox.BODY,
                Telephony.Sms.Inbox.DATE
            ),
            null,
            null,
            Telephony.Sms.Inbox.DEFAULT_SORT_ORDER + " LIMIT " + max + " OFFSET " + skip
        )
        if (pointer != null) {
            pointer.moveToFirst()
            while (!pointer.isAfterLast) {
                val sender =
                    pointer.getString(pointer.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                val time = pointer.getLong(pointer.getColumnIndexOrThrow(Telephony.Sms.DATE))
                val body = pointer.getString(pointer.getColumnIndexOrThrow(Telephony.Sms.BODY))

                allMessages.add(Message(sender, time, body, body.containsOtp()))

                pointer.moveToNext()
            }
            pointer.close()
        }
        return allMessages
    }
}