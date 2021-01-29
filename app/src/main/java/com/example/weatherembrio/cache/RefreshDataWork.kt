package com.example.weatherembrio.cache

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherembrio.data.OpenWeatherRepository
import com.example.weatherembrio.db.ForecastDatabase
import retrofit2.HttpException

class RefreshDataWork(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result  {
        val database = ForecastDatabase.getInstance(applicationContext)
        val repository = OpenWeatherRepository(database)

        return try {
            repository.refreshForecast()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}