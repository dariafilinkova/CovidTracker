package com.example.covidtracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.covidtracker.databinding.FragmentCovidTrackerBinding
import org.eazegraph.lib.models.PieModel

import android.graphics.Color
import android.os.Handler
import androidx.fragment.app.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*
import java.text.SimpleDateFormat
import javax.security.auth.callback.Callback


class CovidTrackerFragment : Fragment() {

    private lateinit var binding: FragmentCovidTrackerBinding
    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var listOfCountryData: List<CountryData>
    val api = CovidTrackerAPIProvider.api
    private val viewModel: CovidTrackerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCovidTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        api.getCountryData()

        FragmentCovidTrackerBinding.bind(view).apply {
            var date = binding.date
            date.setText(SimpleDateFormat().format(Calendar.getInstance().time).toString())
            var chosenCountry = countryName.text.toString()
            //binding.totalActive.text= viewModel.getDataOfCountry(chosenCountry).toString()
            //var totalActive=binding.totalActive
            //totalActive.setText(api.getCountryData().toString())
            //val currentCountry =
             viewModel.getDataOfCountry(chosenCountry)

            //viewModel.getDataOfCountry(chosenCountry)
          // scope.launch {
            //    val currentCountry = api.getCountryInfo(chosenCountry)
              //  binding.totalActive.text=currentCountry.active
          // binding.totalActive.text=api.getCountryInfo(chosenCountry).active
            //}
            piechart.apply {
                addPieSlice(PieModel("Confirm", 15F, Color.parseColor("#FBC233")))
                addPieSlice(PieModel("Active", 25F, Color.parseColor("#007afe")))
                addPieSlice(PieModel("Recovered", 35F, Color.parseColor("#08a045")))
                addPieSlice(PieModel("Death", 9F, Color.parseColor("#F6404F")))
                startAnimation()
            }
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