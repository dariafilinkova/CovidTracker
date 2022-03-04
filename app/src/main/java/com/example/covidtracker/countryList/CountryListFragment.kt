package com.example.covidtracker.countryList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidtracker.data.CountryAdapter
import com.example.covidtracker.databinding.FragmentCountryListBinding


class CountryListFragment : Fragment() {
    private lateinit var binding: FragmentCountryListBinding
    private val viewModel: CountryListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryListBinding.inflate(inflater, container, false)
        binding.refresh.setOnRefreshListener {
            viewModel.getData()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        val adapter = CountryAdapter()
        val layoutManager = LinearLayoutManager(requireContext())

        FragmentCountryListBinding.bind(view).apply {
            listOfCountries.addItemDecoration(dividerItemDecoration)
            viewModel.getData()
            listOfCountries.adapter = adapter
            listOfCountries.layoutManager = layoutManager
            viewModel.countryLiveData.observe(viewLifecycleOwner) {
                binding.refresh.isRefreshing = false
                adapter.country = it

            }
        }
    }

    companion  object {

        @JvmStatic
        fun newInstance(): CountryListFragment {
            return CountryListFragment()
        }
    }
}