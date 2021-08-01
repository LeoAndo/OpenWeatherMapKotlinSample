package com.example.openweathermapkotlinsample.ui.notifications

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.example.openweathermapkotlinsample.R

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = view.findViewById<WebView>(R.id.webView)
        webView.loadUrl("https://openweathermap.org/api")
    }
}