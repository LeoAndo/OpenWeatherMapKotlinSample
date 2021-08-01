package com.example.openweathermapkotlinsample.ui

import android.view.View
import com.example.openweathermapkotlinsample.R
import com.example.openweathermapkotlinsample.databinding.ItemTodayWeatherBinding
import com.example.openweathermapkotlinsample.domain.WeatherModel
import com.xwray.groupie.viewbinding.BindableItem
import java.text.SimpleDateFormat
import java.util.*

// 天気詳細に表示する今日の天気リストのセル.
class TodayWeatherItem(val info: WeatherModel) : BindableItem<ItemTodayWeatherBinding>() {
    override fun getLayout(): Int = R.layout.item_today_weather
    override fun bind(viewBinding: ItemTodayWeatherBinding, position: Int) {
        viewBinding.info = info
        viewBinding.textTime.text =
            getSimpleDateFormat(DateFormatPattern.HHMM.pattern).format(info.day)
    }

    override fun initializeViewBinding(view: View): ItemTodayWeatherBinding =
        ItemTodayWeatherBinding.bind(view)

    private fun getSimpleDateFormat(pattern: String): SimpleDateFormat {
        return SimpleDateFormat(pattern, Locale.JAPAN).apply {
            timeZone = AppTimeZone.TOKYO.timeZone
        }
    }
}