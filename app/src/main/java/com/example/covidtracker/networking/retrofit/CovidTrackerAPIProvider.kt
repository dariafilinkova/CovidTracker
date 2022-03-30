package com.example.covidtracker.networking.retrofit

import com.example.covidtracker.networking.api.CovidTrackerAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit


object CovidTrackerAPIProvider {
        private const val BASE_URL = "https://disease.sh/v3/covid-19/"
    private val logging = HttpLoggingInterceptor()
    private val client = OkHttpClient.Builder().addInterceptor(logging).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).client(client)
        .build()

    val api = retrofit.create(CovidTrackerAPI::class.java)
}