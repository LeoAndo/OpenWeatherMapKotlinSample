package com.example.openweathermapkotlinsample.data.response

import com.squareup.moshi.Json

data class HourlyWeatherResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @Json(name = "timezone_offset") val timezoneOffset: Long,
    val hourly: List<HourlyInfo>
) {
    data class HourlyInfo(
        val dt: Long,
        val temp: Double,
        @Json(name = "feels_like") val feelsLike: Double,
        val pressure: Long,
        val humidity: Long,
        @Json(name = "dew_point") val dewPoint: Double,
        val clouds: Long,
        val visibility: Long,
        @Json(name = "wind_speed") val windSpeed: Double,
        @Json(name = "wind_deg") val windDeg: Long,
        val weather: List<WeeklyWeatherResponse.Daily>,
        val pop: Double
    )
}