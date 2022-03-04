package com.example.covidtracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.covidtracker.databinding.FragmentCovidTrackerBinding
import org.eazegraph.lib.models.PieModel

import android.graphics.Color
import androidx.fragment.app.viewModels
import com.example.covidtracker.data.CountryData
import com.example.covidtracker.networking.retrofit.CovidTrackerAPIProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*
import java.text.SimpleDateFormat


class CovidTrackerFragment : Fragment() {

    private lateinit var binding: FragmentCovidTrackerBinding
    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var listOfCountryData: List<CountryData>
    val api = CovidTrackerAPIProvider.api
    private val viewModel: CovidTrackerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCovidTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //api.getCountryList()

        FragmentCovidTrackerBinding.bind(view).apply {
            var date = binding.date
            date.setText(SimpleDateFormat().format(Calendar.getInstance().time).toString())
            var chosenCountry = countryName.text.toString()
            viewModel.getDataOfCountry(chosenCountry)

            viewModel.countryInfo.observe(viewLifecycleOwner) { countryData ->
                with(binding) {
                    totalActive.text = countryData.active
                    totalConfirm.text = countryData.cases
                    todayConfirm.text = countryData.todayCases
                    death.text =  countryData.deaths
                    todayDeath.text = countryData.todayDeaths
                    totalRecovered.text=countryData.recovered
                    todayRecovered.text=countryData.todayRecovered
                    totalTests.text=countryData.tests
                    piechart.apply {
                        addPieSlice(PieModel("Confirm", countryData.cases.toFloat() , Color.parseColor("#FBC233")))
                        addPieSlice(PieModel("Active",  countryData.active.toFloat(), Color.parseColor("#FF007AFE")))
                        addPieSlice(PieModel("Recovered", countryData.recovered.toFloat(), Color.parseColor("#FF08A045")))
                        addPieSlice(PieModel("Death", countryData.deaths.toFloat(), Color.parseColor("#F6404F")))
                        startAnimation()
                    }
                }

            }
            // scope.launch {
            //    val currentCountry = api.getCountryInfo(chosenCountry)
            //  binding.totalActive.text=currentCountry.active
            // binding.totalActive.text=api.getCountryInfo(chosenCountry).active
            //}
            /*piechart.apply {
                addPieSlice(PieModel("Confirm", countryData.tests , Color.parseColor("#FBC233")))
                addPieSlice(PieModel("Active", 25F, Color.parseColor("#007afe")))
                addPieSlice(PieModel("Recovered", 35F, Color.parseColor("#08a045")))
                addPieSlice(PieModel("Death", 9F, Color.parseColor("#F6404F")))
                startAnimation()
            }*/
//            piechart.addPieSlice(PieModel("Confirm", 15F, Color.parseColor("#FE6DA8")))
//            piechart.addPieSlice(PieModel("Sleep", 25F, Color.parseColor("#56B7F1")))
//            piechart.addPieSlice(PieModel("Work", 35F, Color.parseColor("#CDA67F")))
//            piechart.addPieSlice(PieModel("Eating", 9F, Color.parseColor("#FED70E")))
//            piechart.startAnimation()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = CovidTrackerFragment()
    }
}