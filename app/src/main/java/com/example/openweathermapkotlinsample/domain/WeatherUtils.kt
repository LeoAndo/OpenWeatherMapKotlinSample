package com.example.openweathermapkotlinsample.domain

import android.util.Log
import com.example.openweathermapkotlinsample.R

object WeatherUtils {
    fun convertWeatherIcon(description: String): Int {
        return when (description) {
            "clear sky" -> R.drawable.ic_weather_clear_sky
            "few clouds" -> R.drawable.ic_weather_few_clouds
            "scattered clouds", "broken clouds", "overcast clouds" -> R.drawable.ic_weather_clouds
            "heavy intensity rain", "shower rain", "light rain", "moderate rain", "rain" -> R.drawable.ic_weather_rain
            "thunderstorm" -> R.drawable.ic_weather_thunderstorm
            "snow" -> R.drawable.ic_weather_snow
            "mist" -> R.drawable.ic_weather_mist
            else -> {
                Log.w("TAG", "noimage description: $description")
                R.drawable.img_no_image_placeholder
            }
        }
    }

    fun getTranslation(description: String): String {
        return when (description) {
            "few clouds" -> "曇り"
            "light rain" -> "小雨"
            "moderate rain" -> "雨"
            "heavy intensity rain" -> "大雨"
            "very heavy rain" -> "激しい大雨"
            "clear sky" -> "快晴"
            "shower rain" -> "にわか雨"
            "light intensity shower rain" -> "小雨のにわか雨"
            "heavy intensity shower rain" -> "小雨のにわか雨"
            "thunderstorm" -> "雷雨"
            "snow" -> "雪"
            "mist" -> "靄"
            "tornado" -> "強風"
            else -> {
                Log.w("TAG", "no Translation description: $description")
                description
            }
        }
    }

}

