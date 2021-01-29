package com.example.weatherembrio.network

import com.example.weatherembrio.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://apis.openweathermap.org/"

interface OpenWeatherApiService {

    @GET("data/2.5/forecast")
    suspend fun forecast(@Query("q") cityName: String) : Forecast

}

class OpenWeatherInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("units", "metric")
            .addQueryParameter("appid", BuildConfig.OPEN_WEATHER_API_KEY)
            .build()
        return chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .url(url)
                .build()
        )
    }
}

private val client = OkHttpClient.Builder()
    .addInterceptor(OpenWeatherInterceptor())
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

object OpenWeatherApi {
    val retrofitService : OpenWeatherApiService by lazy {
        retrofit.create(OpenWeatherApiService::class.java)
    }
}

