package com.example.covidtracker

import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit
import kotlin.coroutines.CoroutineContext


object CovidTrackerAPIProvider {
    private const val BASE_URL = "https://corona.lmao.ninja/v2/"
    val logging = HttpLoggingInterceptor()
    val client = OkHttpClient.Builder().addInterceptor(logging).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).client(client)
        .build()


    val api = retrofit.create(CovidTrackerAPI::class.java)
    /* fun getCountryData(country: String) : CountryData {
         withContext(CoroutineContext){
             api.getCountryInfo(country)
         }
     }*/
}