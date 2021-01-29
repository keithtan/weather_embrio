package com.example.weatherembrio.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<ForecastItem>)

    @Query("SELECT * FROM forecast")
    suspend fun forecast(): List<ForecastItem>

    @Query("DELETE FROM forecast")
    suspend fun clearForecast()
}