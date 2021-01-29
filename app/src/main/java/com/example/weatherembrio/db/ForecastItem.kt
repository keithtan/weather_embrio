package com.example.weatherembrio.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastItem(
    @PrimaryKey val dt: Long,
    val temp: Double?,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
    val main: String,
    val description: String,
    val icon: String
)