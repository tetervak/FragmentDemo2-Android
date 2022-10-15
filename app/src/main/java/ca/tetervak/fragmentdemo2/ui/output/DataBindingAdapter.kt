package ca.tetervak.fragmentdemo2.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@BindingAdapter("dateAndTimeValues")
fun bindDateTime(textView: TextView, date: Date?) {
    if (date is Date){
        textView.text = formatDateAndTime(date)
    }
}

private fun formatDateAndTime(date: Date): String {
    return date.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
        .format(dateAndTimeFormatter)
}

private val dateAndTimeFormatter =
    DateTimeFormatter.ofPattern("MMM dd, yyyy - h:mm a")