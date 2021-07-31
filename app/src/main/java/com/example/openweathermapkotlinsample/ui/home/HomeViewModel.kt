package com.example.openweathermapkotlinsample.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val todayWeatherTemp = ObservableField<String>()
    val todayWeatherTempMax = ObservableField<Int>()
    val todayWeatherTempMin = ObservableField<Int>()
    val todayWeatherDescription = ObservableField<String>()

    init {
        // TODO ダミーデータ
        todayWeatherTemp.set("30℃")
        todayWeatherTempMax.set(34)
        todayWeatherTempMin.set(23)
        todayWeatherDescription.set("snow")
    }
}