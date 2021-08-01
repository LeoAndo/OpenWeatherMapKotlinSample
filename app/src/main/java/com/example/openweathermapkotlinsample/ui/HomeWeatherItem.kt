package com.example.openweathermapkotlinsample.ui

import android.view.View
import com.example.openweathermapkotlinsample.R
import com.example.openweathermapkotlinsample.databinding.ItemHomeWeatherBinding
import com.example.openweathermapkotlinsample.domain.WeatherModel
import com.xwray.groupie.viewbinding.BindableItem
import java.text.SimpleDateFormat
import java.util.*

class HomeWeatherItem(val info: WeatherModel) : BindableItem<ItemHomeWeatherBinding>() {
    override fun getLayout(): Int = R.layout.item_home_weather
    override fun bind(viewBinding: ItemHomeWeatherBinding, position: Int) {
        viewBinding.info = info
        val simpleDateFormat =
            SimpleDateFormat(DateFormatPattern.E_JP.pattern, Locale.JAPAN).apply {
                timeZone = AppTimeZone.TOKYO.timeZone
            }
        val labelWeek = simpleDateFormat.format(info.day)
        viewBinding.labelWeek = labelWeek
    }

    override fun initializeViewBinding(view: View): ItemHomeWeatherBinding =
        ItemHomeWeatherBinding.bind(view)
}