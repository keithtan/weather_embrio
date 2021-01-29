package com.example.weatherembrio.forecast

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("day")
fun TextView.bindDay(dt: Long?) {
    text = dt?.let {
        val date = Date(it.times(1000))
        SimpleDateFormat("EE", Locale.ENGLISH)
            .format(date)
    }
}

@BindingAdapter("hour")
fun TextView.bindHour(dt: Long?) {
    text = dt?.let {
        val calendar = Calendar.getInstance()
        calendar.time = Date(it.times(1000))
        calendar.get(Calendar.HOUR).toString() +
                if (calendar.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
    }
}

@BindingAdapter("icon")
fun ImageView.bindIcon(iconUrl: String?) {
    iconUrl?.let {
        val fullUrl = "https://openweathermap.org/img/wn/$iconUrl@2x.png"
        val imgUri = fullUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .into(this)
    }
}
