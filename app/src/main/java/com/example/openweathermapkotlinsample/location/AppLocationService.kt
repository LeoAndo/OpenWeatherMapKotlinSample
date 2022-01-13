package com.example.openweathermapkotlinsample.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.thread

private const val LOG_TAG = "AppLocationService"

interface AppLocationService {
    val locationClient: FusedLocationProviderClient
    val locationResult: LiveData<LocationData?>
    val localityName: LiveData<String>

    // 現在地の取得を行う.
    fun getCurrentLocation()
}

class AppLocationServiceImpl @Inject constructor(@ApplicationContext val context: Context) :
    AppLocationService {

    override val locationClient: FusedLocationProviderClient
        get() = LocationServices.getFusedLocationProviderClient(context)
    private val _locationResult = MutableLiveData<LocationData?>()
    override val locationResult: LiveData<LocationData?>
        get() = _locationResult
    private val _localityName = MutableLiveData<String>()
    override val localityName: LiveData<String>
        get() = _localityName

    override fun getCurrentLocation() {
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(context, "天気機能を使用するには、アプリの位置情報パーミッションを許可する必要があります", Toast.LENGTH_LONG)
                .show()
            _locationResult.value = null
            return
        }

        locationClient.lastLocation.addOnSuccessListener { location ->
            Log.d(LOG_TAG, "location: $location")
            if (location != null) {
                _locationResult.value =
                    LocationData(latitude = location.latitude, longitude = location.longitude)
                getLocalityName(latitude = location.latitude, longitude = location.longitude)
            } else {
                val request = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(500)
                    .setFastestInterval(300)
                locationClient
                    .requestLocationUpdates(
                        request,
                        object : LocationCallback() {
                            override fun onLocationResult(result: LocationResult) {
                                Log.d(
                                    LOG_TAG,
                                    "onLocationResult latitude: ${result.lastLocation.latitude} longitude: ${result.lastLocation.longitude}"
                                )
                                _locationResult.value = LocationData(
                                    latitude = result.lastLocation.latitude,
                                    longitude = result.lastLocation.longitude
                                )
                                // 現在地だけ欲しいので、1回取得したら位置情報取得をやめる
                                locationClient.removeLocationUpdates(this)
                                getLocalityName(
                                    latitude = result.lastLocation.latitude,
                                    longitude = result.lastLocation.longitude
                                )
                            }

                            override fun onLocationAvailability(p0: LocationAvailability) {
                                super.onLocationAvailability(p0)
                                // 位置情報OFFの時にここに入る
                                Log.d(LOG_TAG, "onLocationAvailability")
                                Toast.makeText(
                                    context,
                                    "天気機能を使用するには、端末の位置情報設定をONにする必要があります",
                                    Toast.LENGTH_LONG
                                ).show()
                                _locationResult.value = null
                            }
                        },
                        Looper.getMainLooper()
                    )
            }
        }.addOnFailureListener {
            Log.d(LOG_TAG, "error: $it")
            Toast.makeText(
                context, "現在地取得に失敗しました", Toast
                    .LENGTH_LONG
            ).show()
            _locationResult.value = null
        }
    }

    private fun getLocalityName(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(context, Locale.getDefault())
        thread {
            kotlin.runCatching {
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (addresses.isNotEmpty()) {
                    _localityName.postValue(addresses[0].locality)
                }
            }.onFailure {
                Log.e(LOG_TAG, "error: $it")
            }
        }
    }
}

