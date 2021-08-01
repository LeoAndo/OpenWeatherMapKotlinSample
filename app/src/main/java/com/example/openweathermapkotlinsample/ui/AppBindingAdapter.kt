package com.example.openweathermapkotlinsample

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.openweathermapkotlinsample.domain.WeatherUtils

@BindingAdapter("formatTemp")
fun TextView.setFormatTemp(temp: Int) {
    text = context.getString(R.string.format_temp, temp)
}

@BindingAdapter("formatDegreeCelsius")
fun TextView.setFormatDegreeCelsius(temp: Int) {
    text = context.getString(R.string.format_degree_celsius, temp)
}

@BindingAdapter("formatPercentage")
fun TextView.setFormatPercent(score: Int) {
    text = context.getString(R.string.format_percentage, score)
}

@BindingAdapter("weatherIcon")
fun ImageView.setWeatherIcon(description: String?) {
    description ?: return
    val icon = WeatherUtils.convertWeatherIcon(description)
    this.setImageResource(icon)
}