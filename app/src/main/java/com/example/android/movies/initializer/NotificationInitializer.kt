package com.example.android.movies.initializer

import android.content.Context
import androidx.startup.Initializer
import com.example.android.movies.notification.NotificationConfigUtils

class NotificationInitializer : Initializer<NotificationConfigUtils?> {

    override fun create(context: Context): NotificationConfigUtils {
        return NotificationConfigUtils(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf()
    }

}