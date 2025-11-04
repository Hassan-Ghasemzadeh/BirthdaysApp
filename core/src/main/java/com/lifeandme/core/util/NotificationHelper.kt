package com.lifeandme.core.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {
    /**
     * Creates a notification channel for Android O (API 26) and higher.
     * On older versions, notification channels are not supported, so no action is needed.
     *
     * @param context The application context.
     * @param channelId A unique string for the channel ID.
     * @param channelName The user-visible name for the channel.
     */
    fun createChannel(context: Context, channelId: String, channelName: String) {
        // We use android.app.NotificationManager here, which requires the import
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Check if the device is running Android O (API 26) or newer
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH,
            )
            notificationManager.createNotificationChannel(channel)
        } else {
            // On older versions (API < 26), notification channels do not exist,
            // so we don't need to perform any action here.
            // Notifications built without channels will work automatically.
        }
    }

    /**
     * Builds a basic notification using NotificationCompat.Builder for compatibility.
     *
     * @param context The application context.
     * @param channelId The ID of the notification channel (required for API 26+).
     * @param title The title text for the notification.
     * @param content The content text for the notification.
     * @return The built Notification object.
     */
    fun buildNotification(
        context: Context,
        channelId: String,
        title: String,
        content: String
    ) = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setAutoCancel(true)// AutoCancel dismisses the notification when the user taps it
        .build()
}