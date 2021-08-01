package com.example.openweathermapkotlinsample.domain

import java.io.Serializable
import java.util.*

data class WeatherModel(
    val description: String,
    val descJp: String,
    val iconUrl: String,
    val main: String,
    val tempMax: Int,
    val tempMin: Int,
    val temp: Int,
    val day: Date
) : Serializable