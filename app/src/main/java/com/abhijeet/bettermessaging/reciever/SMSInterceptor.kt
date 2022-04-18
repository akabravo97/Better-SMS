package com.abhijeet.bettermessaging.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abhijeet.bettermessaging.R
import com.abhijeet.bettermessaging.utils.findOtpLocation

class SMSInterceptor : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) return
        val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        extractMessages.forEach { smsMessage ->
            if (smsMessage.messageBody.contains("OTP")) {
                val pair = smsMessage.messageBody.findOtpLocation()
                createNotification(
                    context,
                    smsMessage.messageBody.substring(pair.first, pair.second - pair.first + 1)
                )
            }
        }
    }

    private fun createNotification(context: Context?, otp: String) {
        var builder = NotificationCompat.Builder(context!!, "otp_channel")
            .setSmallIcon(R.drawable.ic_message)
            .setContentTitle("OTP Recieved")
            .setContentText(otp)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(0, builder.build())
        }
    }
}