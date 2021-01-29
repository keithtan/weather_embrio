package com.example.weatherembrio.data

import com.example.weatherembrio.db.ForecastDatabase
import com.example.weatherembrio.db.ForecastItem
import com.example.weatherembrio.network.ForecastItemDto
import com.example.weatherembrio.network.OpenWeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class OpenWeatherRepository(private val database: ForecastDatabase) {

    fun getForecastFlow(): Flow<List<ForecastItem>> = flow {
        emit(database.forecastDao().forecast())
    }

    suspend fun refreshForecast() {
        withContext(Dispatchers.IO) {
            try {
                val forecast = OpenWeatherApi.retrofitService.forecast("singapore")
                database.forecastDao().clearForecast()
                database.forecastDao().insertAll(forecast.list.asDomainModel())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun List<ForecastItemDto>.asDomainModel(): List<ForecastItem> {
        return map {
            ForecastItem(
                it.dt,
                it.main.temp,
                it.main.feelsLike,
                it.main.tempMin,
                it.main.tempMax,
                it.main.pressure,
                it.main.humidity,
                it.weather[0].main,
                it.weather[0].description,
                it.weather[0].icon
            )
        }
    }

}