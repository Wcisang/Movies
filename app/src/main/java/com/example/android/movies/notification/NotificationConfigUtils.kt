package com.example.android.movies.notification

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.graphics.Color
import android.os.Build

class NotificationConfigUtils(private val context: Context) {

    init {
        createMainNotificationChannel()
    }

    private fun createMainNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val id = CHANNEL_ID
            val name = CHANNEL_NAME
            val description = CHANNEL_DESCRIPTION
            val importance = android.app.NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(id, name, importance)
            mChannel.description = description
            mChannel.enableLights(true)
            mChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            mNotificationManager.createNotificationChannel(mChannel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "YOUR_CHANNEL_ID"
        private const val CHANNEL_NAME = "Miscellaneous"
        private const val CHANNEL_DESCRIPTION = "description"
    }
}