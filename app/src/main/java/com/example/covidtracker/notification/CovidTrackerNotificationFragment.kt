package com.example.covidtracker.notification

import android.R
import android.app.*
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
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
            if (binding.date.text.isNullOrEmpty()) {
                Log.d("null date", "empty field of date")
                showToast()
            } else {
                val enteredTime = LocalDateTime.of(year, month + 1, day, hour, minute)
                val today = LocalDateTime.now()
                val timeDifference = ChronoUnit.MILLIS.between(today, enteredTime);
                val workRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                    .setInitialDelay(timeDifference, TimeUnit.MILLISECONDS)
                    .setInputData(
                        workDataOf(
                            "titleExtra" to binding.titleNotification.text.toString(),
                            "messageExtra" to binding.messageNotification.text.toString()
                        )
                    )
                    .build()
                showAlert(
                    enteredTime,
                    binding.titleNotification.text.toString(),
                    binding.messageNotification.text.toString()
                )

                WorkManager.getInstance(requireContext()).enqueue(workRequest)
            }
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

    private fun showToast() {
        AlertDialog.Builder(requireContext())
            .setTitle("Empty field of date")
            .setMessage(
                "Please choose the date "
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show().window?.setLayout(1000, 540)
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

    private fun showAlert(time: LocalDateTime, title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + time.dayOfMonth + " " + time.month + " " + time.year + "  "
                        + time.hour + ":" + time.minute
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show()
    }
}