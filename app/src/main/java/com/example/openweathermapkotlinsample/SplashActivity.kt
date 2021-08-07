package com.example.openweathermapkotlinsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.myLooper()!!).postDelayed({
            getToNextScreen();
        }, 2000L)
    }

    private fun getToNextScreen() {
        MainActivity.startActivity(this)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}