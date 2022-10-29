package com.acsoft.saveplacesxml.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.acsoft.saveplacesxml.R
import com.acsoft.saveplacesxml.util.Constants.TAG

object Notifications {
    const val CHANNEL_ID = "CHANNEL_ID"
    private const val NOTIFICATION_CHANNEL_DESCRIPTION = "SHOWS_NOTIFICATION"
    const val NOTIFICATION_ID = 100

    fun makeStatusNotification(title: String,message: String, context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val description = NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }

    fun sleep() {
        try {
            Thread.sleep(7000, 0)
        } catch (e: InterruptedException) {
            Log.e(TAG, e.message.toString())
        }

    }


}