package com.harnet.dogbreeds.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.harnet.dogbreeds.R
import com.harnet.dogbreeds.view.MainActivity

class NotificationsHelper(val context: Context) {
    private var CHANNEL_ID = "Updating channel"// channel to updates notifications
    private var NOTIFICATION_ID = 111// id for the certain notification

    //create notification
    fun createNotification(){
        // create a channel
        createNotChannel()

        // if user click notification it's go to Main activity and create a new task
        // if it doesn't exist or clears the existing task
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        // manage the action when user click on an icon
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        // define notification icon as a bitmap
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.dog_icon_sm)

        //create a notification
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.dog_icon)
            .setLargeIcon(icon)
            .setContentTitle("Get dogs from API")
            .setContentText("Update list of dog breeds")
                // when the notification was expanded shows large icon and a large image
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(icon)
                .bigLargeIcon(null)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    // create notification channel
    private fun createNotChannel(){
        //check if user has Android'O'(26) and later
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = CHANNEL_ID
            val descrText = "Channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            // define a channel
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descrText
            }
            // register channel
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}