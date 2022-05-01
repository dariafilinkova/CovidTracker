package com.example.covidtracker.globalCovid

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.covidtracker.R
import com.example.covidtracker.countryList.CountryListViewModel

class CovidTrackerGlobalFragment : Fragment() {


    private val viewModel : CovidTrackerGlobalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.covid_tracker_global_fragment, container, false)
    }

    companion object {
        fun newInstance() = CovidTrackerGlobalFragment()
    }

}