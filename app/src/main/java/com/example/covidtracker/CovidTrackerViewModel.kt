package com.example.covidtracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covidtracker.data.CountryData
import com.example.covidtracker.networking.retrofit.CovidTrackerAPIProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CovidTrackerViewModel : ViewModel() {
    private val api = CovidTrackerAPIProvider.api
    private val _countryInfo = MutableLiveData<CountryData>()
    val countryInfo: LiveData<CountryData> = _countryInfo

    fun getDataOfCountry(country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _countryInfo.postValue(api.getCountryInfo(country))
            } catch (e: Exception) {
                Log.e("TAG", e.message.orEmpty())
            }
        }
    }
}