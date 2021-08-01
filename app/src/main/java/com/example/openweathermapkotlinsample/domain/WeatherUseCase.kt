package com.example.openweathermapkotlinsample.domain

import com.example.openweathermapkotlinsample.data.WeatherRepository
import java.util.*
import javax.inject.Inject

const val WEEKLY_ITEM_COUNT = 7
interface WeatherUseCase {
    // 今日の天気を取得する
    suspend fun getWeatherInfo(latitude: String, longitude: String): WeatherModel

    // 週間天気を取得する
    suspend fun getWeeklyWeatherInfo(latitude: String, longitude: String): List<WeatherModel>

    // 1時間毎の天気を取得する
    suspend fun getHourlyWeather(latitude: String, longitude: String): List<WeatherModel>
}

class WeatherUseCaseImpl @Inject constructor(private val weatherRepository: WeatherRepository) :
    WeatherUseCase {

    override suspend fun getWeatherInfo(latitude: String, longitude: String): WeatherModel {
        val result = weatherRepository.loadWeatherInfo(latitude, longitude)
        // UIモデルに変換
        val weather = result.weather[0]
        val main = result.main
        val iconUrl = "https://openweathermap.org/img/w/" + weather.icon + ".png"
        val descJp = WeatherUtils.getTranslation(weather.description)
        return WeatherModel(
            description = weather.description,
            descJp = descJp,
            iconUrl = iconUrl,
            main = weather.main,
            temp = main.temp.toInt(),
            tempMax = main.temp_max.toInt(),
            tempMin = main.temp_min.toInt(),
            day = Date()
        )
    }

    override suspend fun getWeeklyWeatherInfo(
        latitude: String,
        longitude: String
    ): List<WeatherModel> {
        val result = weatherRepository.loadWeeklyWeatherInfo(latitude, longitude)
        val daily = result.daily
        val models = arrayListOf<WeatherModel>()
        daily.map { data ->
            val day = Date(data.dt * 1000L)
            val weather = data.weather[0]
            val temp = data.temp
            val iconUrl = "https://openweathermap.org/img/w/" + weather.icon + ".png"
            val descJp = WeatherUtils.getTranslation(weather.description)
            val model = WeatherModel(
                description = weather.description,
                descJp = descJp,
                iconUrl = iconUrl,
                main = weather.main,
                tempMax = temp.max.toInt(),
                tempMin = temp.min.toInt(),
                temp = temp.day.toInt(),
                day = day
            )
            models.add(model)
        }
        return models.take(WEEKLY_ITEM_COUNT)
    }

    override suspend fun getHourlyWeather(latitude: String, longitude: String): List<WeatherModel> {
        val result = weatherRepository.loadHourlyWeather(latitude, longitude)
        val hourly = result.hourly
        val models = arrayListOf<WeatherModel>()
        hourly.map { data ->
            val day = Date(data.dt * 1000)
            val weather = data.weather[0].weather[0]
            val temp = data.temp
            val iconUrl = "https://openweathermap.org/img/w/" + weather.icon + ".png"
            val descJp = WeatherUtils.getTranslation(weather.description)
            val model = WeatherModel(
                description = weather.description,
                descJp = descJp,
                iconUrl = iconUrl,
                main = weather.main,
                tempMax = temp.toInt(), // 時間単位の取得では最高気温取れない
                tempMin = temp.toInt(), // 時間単位の取得では最低気温取れない
                temp = temp.toInt(),
                day = day
            )
            models.add(model)
        }
        return models
    }
}