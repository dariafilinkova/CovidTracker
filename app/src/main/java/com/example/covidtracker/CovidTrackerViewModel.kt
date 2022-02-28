package com.example.covidtracker

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CovidTrackerViewModel : ViewModel() {
    val api = CovidTrackerAPIProvider.api
    fun getDataOfCountry(country: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                api.getCountryInfo(country).active
            }
        } catch (e: Exception) {
            Log.e("TAG", e.message.orEmpty())
        }
    }
}