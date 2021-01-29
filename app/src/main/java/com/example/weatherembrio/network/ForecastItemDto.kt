package com.example.weatherembrio.network

import com.squareup.moshi.Json

data class ForecastItemDto(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>
) {
    data class Main(
        val temp: Double,
        @Json(name = "feels_like") val feelsLike: Double,
        @Json(name = "temp_min") val tempMin: Double,
        @Json(name = "temp_max") val tempMax: Double,
        val pressure: Int,
        val humidity: Int
    )

    data class Weather(
        val main: String,
        val description: String,
        val icon: String
    )
}