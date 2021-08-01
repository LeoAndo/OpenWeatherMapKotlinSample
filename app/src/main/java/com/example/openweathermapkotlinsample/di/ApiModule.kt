package com.example.openweathermapkotlinsample.di

import com.example.openweathermapkotlinsample.BuildConfig
import com.example.openweathermapkotlinsample.data.OpenWeatherMapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    @Singleton
    @Provides
    internal fun provideUnsplashService(): OpenWeatherMapService {
        val okHttpClientBuilder = Network.createOkHttpClientBuilder(BuildConfig.DEBUG)
        return Retrofit.Builder()
            .callFactory { request -> okHttpClientBuilder.build().newCall(request) }
            .baseUrl(BuildConfig.WEATHER_API_DOMAIN)
            .addConverterFactory(MoshiConverterFactory.create(Network.createMoshi()))
            .build()
            .create(OpenWeatherMapService::class.java)
    }
}