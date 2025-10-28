package com.example.creditcardmanager.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Utils {

    fun formatDateToMonth(date: Date): String {
        val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
        return dateFormat.format(date)

    }

    fun getCurrentMonth(): String {
        val monthFormatter = SimpleDateFormat("MMM", Locale.getDefault())
        return monthFormatter.format(Calendar.getInstance().time)
    }
}