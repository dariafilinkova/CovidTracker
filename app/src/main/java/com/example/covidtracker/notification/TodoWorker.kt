package com.example.covidtracker.notification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class TodoWorker(val context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        NotificationHelper(context).createNotification(
            inputData.getString("titleExtra").toString(),
            inputData.getString("messageExtra").toString()
        )
        return Result.success()
    }
}