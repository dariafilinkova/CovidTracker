package com.example.covidtracker.data

import com.google.gson.annotations.SerializedName

data class CountryData(
    @SerializedName("updated")
    val updated: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("cases")
    val cases: String,
    @SerializedName("todayCases")
    val todayCases: String,
    @SerializedName("deaths")
    val deaths: String,
    @SerializedName("todayDeaths")
    val todayDeaths: String,
    @SerializedName("recovered")
    val recovered: String,
    @SerializedName("todayRecovered")
    val todayRecovered: String,
    @SerializedName("active")
    val active: String,
    @SerializedName("tests")
    val tests: String,
    @SerializedName("countryInfo")
    val countryInfo:CountryInfo? = null
)

