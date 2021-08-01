package com.example.openweathermapkotlinsample.ui.dashboard

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.openweathermapkotlinsample.BuildConfig
import com.example.openweathermapkotlinsample.R


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private val message: String = """
        あなたが設定したAPI KEYは${BuildConfig.WEATHER_API_KEY}になります。
        設定されていない場合は、local.properties に以下の設定を追加してください。
        (API KEYが abcdの場合の例)
        weatherApiKey=abcd
    """.trimIndent()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.text_dashboard).text = message
    }
}