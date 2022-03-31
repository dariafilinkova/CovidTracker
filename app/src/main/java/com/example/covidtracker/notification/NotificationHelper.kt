package com.example.covidtracker.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.covidtracker.MainActivity
import com.example.covidtracker.R

class NotificationHelper(val context: Context) {
    private val CHANNEL_ID = "Todo_channel_id"
    private val  NOTIFICATION_ID = 234

    fun createNotification(title: String, message: String) {
        createNotificationChannel()
//        val intent = Intent(context, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
        //val pendingIntent = PendingIntent.getActivity(context,0,intent, 0)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            //.setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID,notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "To do channel description"
                }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}