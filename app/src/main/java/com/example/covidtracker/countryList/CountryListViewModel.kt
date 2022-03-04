package com.example.covidtracker.countryList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covidtracker.data.CountryData
import com.example.covidtracker.networking.retrofit.CovidTrackerAPIProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryListViewModel : ViewModel() {
    private val _countryLiveData = MutableLiveData<List<CountryData>>()
    val countryLiveData: LiveData<List<CountryData>> = _countryLiveData
    val api = CovidTrackerAPIProvider.api

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _countryLiveData.postValue(api.getCountryList())
            } catch (e: Exception) {
                Log.e("TAG", e.message.orEmpty())
            }
        }
    }
}
