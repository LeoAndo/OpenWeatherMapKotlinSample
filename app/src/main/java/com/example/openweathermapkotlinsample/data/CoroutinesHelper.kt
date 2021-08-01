package com.example.openweathermapkotlinsample.data

import android.util.Log
import com.example.openweathermapkotlinsample.domain.exception.UnAuthorizedException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> apiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): T {
    return withContext(dispatcher) {
        Log.d("apiCall", "currentThread: ${Thread.currentThread().name}")
        try {
            apiCall.invoke()
        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    // エラーコードによってthrowするErrorを変える.
                    when (e.code()) {
                        HttpURLConnection.HTTP_UNAUTHORIZED -> throw UnAuthorizedException(e.message)
                        else -> throw e
                    }
                }
                is ConnectException, is UnknownHostException, is SocketTimeoutException -> throw e
                else -> throw e
            }
        }
    }
}