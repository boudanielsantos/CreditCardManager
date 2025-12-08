package com.example.creditcardmanager.utils

import androidx.compose.animation.core.copy
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.model.CreditCardStatus
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Utils {

    fun formatDateToMonth(date: Date): String {
        val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
        return dateFormat.format(date)

    }

    fun getMonthName(monthIndex: Int?): String {
        if (monthIndex == null) return ""
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, monthIndex)
        val dateFormat = SimpleDateFormat("MMM", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun getStatementDateMonth(day: Int): String {
        val calendar = Calendar.getInstance()
        val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        if (currentDayOfMonth >= day) {
            // If the current date is on or after the statement day, it's for the current month.
            calendar.add(Calendar.MONTH, 0)
        } else {
            // If the current date is before the statement day, it's for the previous month.
            calendar.add(Calendar.MONTH, -1)
        }
        val dateFormat = SimpleDateFormat("MMM", Locale.getDefault())
        return dateFormat.format(calendar.time)

    }

    fun getDueDateMonth(dueDay: Int, statementDay: Int): String {
        val calendar = Calendar.getInstance()
        val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        // Logic assumes the due date is in the month after the statement.
        if (currentDayOfMonth >= statementDay) {
            // If the current date is on or after the statement day, the statement is for this month.
            // Therefore, the due date is next month.
            calendar.add(Calendar.MONTH, 1)
        } else {
            // If the current date is before the statement day, the statement was for the previous month.
            // Therefore, the due date is this month.
            calendar.add(Calendar.MONTH, 0)
        }
        val dateFormat = SimpleDateFormat("MMM", Locale.getDefault()) // [1]
        return dateFormat.format(calendar.time)
    }


    fun updateCreditCardCycle(
        card: CreditCard,
        statementMonthCalendar: Calendar,
        dueDateMonthCalendar: Calendar
    ): CreditCard {
        val today = Calendar.getInstance()
        val lastUpdate = Calendar.getInstance().apply { time = card.lastUpdateDate }

        // Set a calendar to this month's statement day
        val thisMonthStatementDay = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, card.statementDay)
        }

        // Check if a new cycle has begun since the last update.
        // This happens if the last update was before this month's statement day,
        // and today is on or after this month's statement day.
        if (lastUpdate.before(thisMonthStatementDay) && (today.equals(thisMonthStatementDay) || today.after(
                thisMonthStatementDay
            ))
        ) {
            // A new cycle has started, so we need to update the card.
            return card.copy(
                cardStatus = CreditCardStatus.UNPAID,
                lastUpdateDate = Date(),
                currentStatementMonth = statementMonthCalendar.get(Calendar.MONTH),
                currentDueMonth = dueDateMonthCalendar.get(Calendar.MONTH)
            )
        }

        // If no new cycle, return the card as is.
        return card
    }

}