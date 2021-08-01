package com.example.openweathermapkotlinsample.data

import com.example.openweathermapkotlinsample.BuildConfig
import com.example.openweathermapkotlinsample.data.response.HourlyWeatherResponse
import com.example.openweathermapkotlinsample.data.response.WeatherResponse
import com.example.openweathermapkotlinsample.data.response.WeeklyWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapService {

    @GET("data/2.5/weather")
    suspend fun loadWeatherInfo(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ja_JP"
    ): WeatherResponse

    // 週間単位の天気情報を取得する
    // excludeの種類
    // current
    // minutely
    // hourly
    // daily
    // alerts
    @GET("data/2.5/onecall")
    suspend fun loadWeeklyWeatherInfo(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "hourly,minutely,current",
        @Query("lang") lang: String = "ja_JP"
    ): WeeklyWeatherResponse

    // 時間単位の天気情報を取得する
    @GET("data/2.5/onecall")
    suspend fun loadHourlyWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "current,minutely,daily,alerts",
        @Query("lang") lang: String = "ja_JP"
    ): HourlyWeatherResponse
}