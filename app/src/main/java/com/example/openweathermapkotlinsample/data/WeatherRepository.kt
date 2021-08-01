package com.example.openweathermapkotlinsample.data

import com.example.openweathermapkotlinsample.data.response.HourlyWeatherResponse
import com.example.openweathermapkotlinsample.data.response.WeatherResponse
import com.example.openweathermapkotlinsample.data.response.WeeklyWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface WeatherRepository {
    suspend fun loadWeatherInfo(latitude: String, longitude: String): WeatherResponse

    suspend fun loadWeeklyWeatherInfo(
        latitude: String,
        longitude: String
    ): WeeklyWeatherResponse

    suspend fun loadHourlyWeather(latitude: String, longitude: String): HourlyWeatherResponse
}

class WeatherRepositoryImpl @Inject constructor(private val api: OpenWeatherMapService) :
    WeatherRepository {

    override suspend fun loadWeatherInfo(latitude: String, longitude: String): WeatherResponse {
        return withContext(Dispatchers.IO) { api.loadWeatherInfo(latitude, longitude) }
    }

    override suspend fun loadWeeklyWeatherInfo(
        latitude: String,
        longitude: String
    ): WeeklyWeatherResponse {
        return withContext(Dispatchers.IO) { api.loadWeeklyWeatherInfo(latitude, longitude) }
    }

    override suspend fun loadHourlyWeather(latitude: String, longitude: String): HourlyWeatherResponse {
        return withContext(Dispatchers.IO) { api.loadHourlyWeather(latitude, longitude) }
    }
}