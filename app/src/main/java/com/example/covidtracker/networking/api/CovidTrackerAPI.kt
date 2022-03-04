package com.example.covidtracker.networking.api

import com.example.covidtracker.data.CountryData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidTrackerAPI {

    @GET("countries/{country}")
    suspend fun getCountryInfo(
        @Path("country") method: String,
    ) : CountryData

    @GET("countries")
    suspend fun getCountryList(): List<CountryData>

}