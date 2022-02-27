package com.example.covidtracker

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CovidTrackerAPI {


    @GET("countries/{country}")
    suspend fun getCountryInfo(
        @Query("country") method: String,
    ) : CountryData

    @GET("countries")
    fun getCountryData(): Call<List<CountryData>>

}