package com.example.openweathermapkotlinsample

object WeatherUtils {
    fun convertWeatherIcon(description: String): Int {
        return when (description) {
            "clear sky" -> R.drawable.ic_weather_clear_sky
            "few clouds" -> R.drawable.ic_weather_few_clouds
            "scattered clouds", "broken clouds", "overcast clouds" -> R.drawable.ic_weather_clouds
            "shower rain", "light rain", "moderate rain", "rain" -> R.drawable.ic_weather_rain
            "thunderstorm" -> R.drawable.ic_weather_thunderstorm
            "snow" -> R.drawable.ic_weather_snow
            "mist" -> R.drawable.ic_weather_mist
            else -> R.drawable.img_no_image_placeholder
        }
    }
}

