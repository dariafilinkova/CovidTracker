package com.example.covidtracker.countryList

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidtracker.R
import com.example.covidtracker.data.CountryAdapter
import com.example.covidtracker.data.CountryData
import com.example.covidtracker.databinding.FragmentCountryListBinding
import okhttp3.internal.filterList
import java.util.*


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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Search something"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.search) {
            Toast.makeText(activity, "Settings", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        @JvmStatic
        fun newInstance(): CountryListFragment {
            return CountryListFragment()
        }
    }
}