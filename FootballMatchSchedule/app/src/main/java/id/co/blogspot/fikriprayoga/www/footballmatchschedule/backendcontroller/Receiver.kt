package id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import android.widget.Toast
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.MainActivity
import java.util.*


class Receiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("DICODING", "MainActivity/352 : " + Date().toString())

        val mIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0)

        val mBuilder = NotificationCompat.Builder(context, "FMS")
                .setSmallIcon(R.drawable.ic_notifications_accent_24dp)
                .setContentTitle("Football Match Schedule")
                .setContentText(intent.getStringExtra("textContent"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("FMS", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(intent.getIntExtra("notificationId", 0), mBuilder.build())
        }

        try {
            RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).play()

        } catch (e: Exception) {}


    }

}