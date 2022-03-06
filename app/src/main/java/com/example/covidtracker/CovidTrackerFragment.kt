package com.example.covidtracker

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.covidtracker.databinding.FragmentCovidTrackerBinding
import org.eazegraph.lib.models.PieModel
import android.graphics.Color
import android.view.*
import androidx.fragment.app.viewModels
import com.example.covidtracker.countryList.CountryListFragment
import java.util.*
import java.text.SimpleDateFormat


class CovidTrackerFragment : Fragment() {

    private lateinit var binding: FragmentCovidTrackerBinding
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

        FragmentCovidTrackerBinding.bind(view).apply {
            var date = binding.date
            date.setText(SimpleDateFormat().format(Calendar.getInstance().time).toString())
            var chosenCountry = countryName.text.toString()
            viewModel.getDataOfCountry(chosenCountry)

            viewModel.countryInfo.observe(viewLifecycleOwner) { countryData ->
                with(binding) {
                    binding.refresh.isRefreshing = false
                    totalActive.text = countryData.active
                    totalConfirm.text = countryData.cases
                    todayConfirm.text = "+ "+countryData.todayCases
                    death.text = countryData.deaths
                    todayDeath.text = "+ "+countryData.todayDeaths
                    totalRecovered.text = countryData.recovered
                    todayRecovered.text = "+ "+countryData.todayRecovered
                    totalTests.text = countryData.tests
                    piechart.apply {
                        addPieSlice(
                            PieModel(
                                "Confirm",
                                countryData.cases.toFloat(),
                                Color.parseColor("#FBC233")
                            )
                        )
                        addPieSlice(
                            PieModel(
                                "Active",
                                countryData.active.toFloat(),
                                Color.parseColor("#FF007AFE")
                            )
                        )
                        addPieSlice(
                            PieModel(
                                "Recovered",
                                countryData.recovered.toFloat(),
                                Color.parseColor("#FF08A045")
                            )
                        )
                        addPieSlice(
                            PieModel(
                                "Death",
                                countryData.deaths.toFloat(),
                                Color.parseColor("#F6404F")
                            )
                        )
                        startAnimation()
                    }
                }
            }
            countryName.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, CountryListFragment.newInstance())
                    .commit()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CovidTrackerFragment()
    }
}