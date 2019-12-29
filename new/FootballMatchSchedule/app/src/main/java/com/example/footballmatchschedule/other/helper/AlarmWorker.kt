package com.example.footballmatchschedule.other.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.footballmatchschedule.R


class AlarmWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val idEvent = inputData.getString("idEvent")
        val strLeague = inputData.getString("strLeague") ?: ""
        val strEvent = inputData.getString("strEvent") ?: ""

        if (idEvent != null) {
            createAlarm(idEvent.toInt(), strLeague, strEvent)

        }

        return Result.success()
    }

    private fun createAlarm(notificationId: Int, strLeague: String, strEvent: String) {
        var channelId = "football_match_schedule"

        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, strLeague, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
            channelId = channel.id

        }

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_notifications_accent_24dp)
            .setContentTitle(strLeague)
            .setContentText(strEvent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(notificationId, builder.build())

    }

}