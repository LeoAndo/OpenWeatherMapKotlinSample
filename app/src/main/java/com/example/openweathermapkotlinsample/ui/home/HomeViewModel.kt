package com.example.openweathermapkotlinsample.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openweathermapkotlinsample.domain.WeatherModel
import com.example.openweathermapkotlinsample.domain.WeatherUseCase
import com.example.openweathermapkotlinsample.location.AppLocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usecase: WeatherUseCase,
    private val appLocationService: AppLocationService
) : ViewModel() {
    val todayWeatherTemp = ObservableField<String>()
    val todayWeatherTempMax = ObservableField<Int>()
    val todayWeatherTempMin = ObservableField<Int>()
    val todayWeatherDescription = ObservableField<String>()
    private val _weatherModels = MutableLiveData<List<WeatherModel>>()
    val weatherModels: LiveData<List<WeatherModel>> = _weatherModels
    private val _hourlyWeatherModels = MutableLiveData<List<WeatherModel>>()
    val hourlyWeatherModels: LiveData<List<WeatherModel>> = _hourlyWeatherModels

    suspend fun fetchWeatherInfo() {
        appLocationService.locationResult.value?.let {
            // 同期処理だと時間かかるので、並列処理でWebAPIを呼び出す
            withContext(Dispatchers.IO) {
                val deferred = listOf(
                    async { getWeeklyWeatherInfo(it.latitude, it.longitude) },
                    async { getWeatherInfo(it.latitude, it.longitude) },
                    async { getHourlyWeather(it.latitude, it.longitude) }
                )
                deferred.awaitAll()
            }
        }
    }

    // 天気予報の情報取得を行う
    private suspend fun getWeeklyWeatherInfo(latitude: Double, longitude: Double) {
        val models = usecase.getWeeklyWeatherInfo(
            latitude = latitude.toString(),
            longitude = longitude.toString()
        )
        _weatherModels.postValue(models)
    }

    // 今日の天気情報を取得する
    private suspend fun getWeatherInfo(latitude: Double, longitude: Double) {
        val weatherInfo = usecase.getWeatherInfo(
            latitude = latitude.toString(),
            longitude = longitude.toString()
        )
        withContext(Dispatchers.Main) {
            todayWeatherTemp.set(weatherInfo.temp.toString())
            todayWeatherTempMax.set(weatherInfo.tempMax)
            todayWeatherTempMin.set(weatherInfo.tempMin)
            todayWeatherDescription.set(weatherInfo.description)
        }
    }

    // 1時間毎の天気を取得する
    private suspend fun getHourlyWeather(latitude: Double, longitude: Double) {
        val models = usecase.getHourlyWeather(latitude.toString(), longitude.toString())
        _hourlyWeatherModels.postValue(models)
    }
}