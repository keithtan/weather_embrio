package com.example.weatherembrio.forecast

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.weatherembrio.data.OpenWeatherRepository
import com.example.weatherembrio.db.ForecastDatabase
import com.example.weatherembrio.db.ForecastItem
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ForecastViewModel constructor(
    application: Application
) : AndroidViewModel(application) {

    private val database = ForecastDatabase.getInstance(application)
    private val repository = OpenWeatherRepository(database)

    init {
        viewModelScope.launch {
            repository.refreshForecast()
        }
    }

    private val forecastItemList: LiveData<List<ForecastItem>> = repository.getForecastFlow()


    val forecastItemListDropped: LiveData<List<ForecastItem>> = forecastItemList.map { value ->
        value.drop(1)
    }

    val current: LiveData<ForecastItem?> = forecastItemList.map { value ->
        if (value.isEmpty()) null
        else value[0]
    }

    val background: LiveData<Int?> = forecastItemList.map { value ->
        if (value.isEmpty()) null
        else {
            when (value[0].main) {
                WeatherType.THUNDERSTORM.key -> Color.rgb(88,62,129)
                WeatherType.CLEAR.key -> Color.rgb(107,191,226)
                WeatherType.RAIN.key -> Color.rgb(77,142,222)
                WeatherType.CLOUDS.key -> Color.rgb(81,207,186)
                WeatherType.DRIZZLE.key -> Color.rgb(107,191,226)
                WeatherType.SNOW.key -> Color.rgb(217,247,247)
                else -> 0
            }
        }
    }

    val day = current.map {
        it?.let {
            val date = Date(it.dt.times(1000))
            SimpleDateFormat("EEEE, MMMM dd", Locale.ENGLISH)
                .format(date)
        }
    }


    val hour = current.map {
        it?.let {
            val calendar = Calendar.getInstance()
            calendar.time = Date(it.dt.times(1000))
            calendar.get(Calendar.HOUR).toString() +
                    if (calendar.get(Calendar.AM_PM) == Calendar.AM) " AM" else " PM"
        }
    }

    enum class WeatherType(val key: String) {
        THUNDERSTORM("Thunderstorm"),
        DRIZZLE("Drizzle"),
        RAIN("Rain"),
        SNOW("Snow"),
        CLEAR("Clear"),
        CLOUDS("Clouds")
    }

}