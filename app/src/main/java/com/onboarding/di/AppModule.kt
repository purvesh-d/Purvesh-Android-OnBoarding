package com.onboarding.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.onboarding.data.remote.APIInterface
import com.onboarding.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideApiService(): APIInterface = Retrofit.Builder().addCallAdapterFactory(CoroutineCallAdapterFactory()) .addConverterFactory(GsonConverterFactory.create()).baseUrl(
        BASE_URL).build().create(APIInterface::class.java)
}