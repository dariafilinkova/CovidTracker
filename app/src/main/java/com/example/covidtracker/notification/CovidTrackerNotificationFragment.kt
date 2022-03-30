package com.example.covidtracker.notification

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.covidtracker.databinding.FragmentCovidTrackerNotificationBinding
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit


class CovidTrackerNotificationFragment : Fragment() {

    private lateinit var binding: FragmentCovidTrackerNotificationBinding
    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCovidTrackerNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.date.setOnClickListener {
            showDate()
        }
        binding.time.setOnClickListener {
            showTime()
        }
        binding.submitButton.setOnClickListener {
            val c = LocalDateTime.of(year, month + 1, day, hour, minute)
            val today = LocalDateTime.now()
            val ddf = ChronoUnit.MILLIS.between(today, c);
            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(ddf, TimeUnit.MILLISECONDS)
                .setInputData(
                    workDataOf(
                        "titleExtra" to binding.titleNotification.text.toString(),
                        "messageExtra" to binding.messageNotification.text.toString()
                    )
                )
                .build()
            showAlert(
                c,
                binding.titleNotification.text.toString(),
                binding.messageNotification.text.toString()
            )
            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CovidTrackerNotificationFragment()
    }

    private fun showDate() {
        val currentDate = Calendar.getInstance()
        val listener =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                this.year = year
                this.month = month
                this.day = day
                currentDate.set(Calendar.YEAR, this.year)
                currentDate.set(Calendar.MONTH, this.month)
                currentDate.set(Calendar.DAY_OF_MONTH, this.day)
                binding.date.setText("${month + 1}/$day/$year")
            }
        DatePickerDialog(
            requireContext(), listener,
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTime() {
        val currentTime = Calendar.getInstance()
        val timeListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            this.hour = hour
            this.minute = minute
            currentTime.set(Calendar.HOUR_OF_DAY, this.hour)
            currentTime.set(Calendar.MINUTE, this.minute)
            if (minute < 10) {
                binding.time.setText("${hour}:0$minute")
            } else {
                binding.time.setText("${hour}:$minute")
            }
        }
        TimePickerDialog(
            requireContext(),
            timeListener,
            currentTime.get(Calendar.HOUR_OF_DAY),
            currentTime.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val description = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = description
        val notificationManager =
            requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

//    private fun scheduleNotification() {
//        val intent = Intent(activity?.applicationContext, Notification::class.java)//
//        val title = binding.titleNotification.text.toString()
//        val message = binding.messageNotification.text.toString()
//        intent.putExtra(titleExtra, title)
//        intent.putExtra(messageExtra, message)
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            activity?.applicationContext,
//            notificationID,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val time = getTime()
//
//
////        val uploadWorkRequest: WorkRequest =
////            OneTimeWorkRequestBuilder<UploadWorker>()
////                .build()
//
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            time,
//            pendingIntent
//        )
//       // showAlert(time, title, message)
//    }

//    private fun getTime(): Long {
//        val minute = binding.timePicker.minute
//        val hour = binding.timePicker.hour
//        val day = binding.datePicker.dayOfMonth
//        val month = binding.datePicker.month
//        val year = binding.datePicker.year
//
//        val calendar = Calendar.getInstance()
//        calendar.set(year, month, day, hour, minute)
//        return calendar.timeInMillis
//    }

    private fun showAlert(time: LocalDateTime, title: String, message: String) {
        //  val date = Date(time)
        val dateFormat =
            android.text.format.DateFormat.getLongDateFormat(activity?.applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(activity?.applicationContext)

        AlertDialog.Builder(requireContext())
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + time
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show()
    }
}