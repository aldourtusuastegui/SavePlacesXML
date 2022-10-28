package com.acsoft.saveplacesxml.services

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.acsoft.saveplacesxml.R
import com.acsoft.saveplacesxml.feature_places.presentation.MainActivity
import com.acsoft.saveplacesxml.util.Notifications.CHANNEL_ID
import com.acsoft.saveplacesxml.util.Notifications.NOTIFICATION_ID

class PlaceService : Service() {

    private val TAG: String = "PlaceService"
    private lateinit var musicPlayer: MediaPlayer
    private lateinit var notification: Notification

    init {
        Log.d(TAG,"Service running.....")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"onCreate...")
        initMusic()
        createNotificationChannel()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        musicPlayer.start()
        Log.d(TAG, "Playing music...")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "OnDestroy...")
        musicPlayer.stop()
        Log.d(TAG, "Stop music and stop service...")
    }

    private fun showNotification() {
        val notificationIntent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0)
        notification = Notification
            .Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.notification_subtitle))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setColor(Color.GREEN)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "My service channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun initMusic() {
        musicPlayer = MediaPlayer.create(this, R.raw.mymusic)
        musicPlayer.isLooping = true
        musicPlayer.setVolume(100F,100F)
    }
}