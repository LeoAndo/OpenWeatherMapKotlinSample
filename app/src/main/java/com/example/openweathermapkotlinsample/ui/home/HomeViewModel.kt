package com.example.openweathermapkotlinsample.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openweathermapkotlinsample.domain.WeatherModel
import com.example.openweathermapkotlinsample.domain.WeatherUseCase
import com.example.openweathermapkotlinsample.service.LocationData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usecase: WeatherUseCase
) : ViewModel() {
    val todayWeatherTemp = ObservableField<String>()
    val todayWeatherTempMax = ObservableField<Int>()
    val todayWeatherTempMin = ObservableField<Int>()
    val todayWeatherDescription = ObservableField<String>()
    private val _weatherModels = MutableLiveData<List<WeatherModel>>()
    val weatherModels: LiveData<List<WeatherModel>> = _weatherModels
    private var locationData: LocationData? = null

    init {
        val dummyData = LocationData(35.6809591, 139.7673068)
        viewModelScope.launch {
            fetchWeatherInfo(dummyData)
        }
    }
    private suspend fun fetchWeatherInfo(locationData: LocationData?) {
        this.locationData = locationData
        this.locationData?.let {
            // TODO プログレスバーの表示処理を行う  - START
            withContext(Dispatchers.IO) {
                val deferred = listOf(
                    async { getWeeklyWeatherInfo(it.latitude, it.longitude) },
                    async { getWeatherInfo(it.latitude, it.longitude) }
                )
                deferred.awaitAll()
            }
            // TODO プログレスバーの表示処理を行う - END
        }
    }

    // 天気予報の情報取得を行う
    private suspend fun getWeeklyWeatherInfo(latitude: Double, longitude: Double) {
        val models = usecase.getWeeklyWeatherInfo(
            latitude = latitude.toString(),
            longitude = longitude.toString()
        )
        withContext(Dispatchers.Main) { _weatherModels.value = models }
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
}