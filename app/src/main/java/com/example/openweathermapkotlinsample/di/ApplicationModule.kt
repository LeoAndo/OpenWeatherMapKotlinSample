package com.example.openweathermapkotlinsample.di

import com.example.openweathermapkotlinsample.data.WeatherRepository
import com.example.openweathermapkotlinsample.data.WeatherRepositoryImpl
import com.example.openweathermapkotlinsample.domain.WeatherUseCase
import com.example.openweathermapkotlinsample.domain.WeatherUseCaseImpl
import com.example.openweathermapkotlinsample.location.AppLocationService
import com.example.openweathermapkotlinsample.location.AppLocationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // アプリケーションスコープ
abstract class ApplicationModule {
    @Binds
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    abstract fun bindWeatherUseCase(impl: WeatherUseCaseImpl): WeatherUseCase

    @Singleton
    @Binds
    abstract fun bindAppLocationService(impl: AppLocationServiceImpl): AppLocationService
}