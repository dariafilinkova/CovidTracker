package com.example.covidtracker.countryList

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidtracker.R
import com.example.covidtracker.data.CountryAdapter
import com.example.covidtracker.databinding.FragmentCountryListBinding

class CountryListFragment : Fragment() {
    private lateinit var binding: FragmentCountryListBinding
    private val viewModel: CountryListViewModel by viewModels()
    val adapter = CountryAdapter(::setCountryResult)

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
        val layoutManager = LinearLayoutManager(requireContext())

        FragmentCountryListBinding.bind(view).apply {
            listOfCountries.addItemDecoration(dividerItemDecoration)
            viewModel.getData()

            listOfCountries.adapter = adapter
            listOfCountries.layoutManager = layoutManager

            viewModel.countryLiveData.observe(viewLifecycleOwner) {
                refresh.isRefreshing = false
                adapter.country = it
            }

        }

    }

    private fun setCountryResult(country:String){
        setFragmentResult("request_key", bundleOf(("country" to country)))
        findNavController().popBackStack()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val menuItem = menu.findItem(R.id.search)
        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.country = if (!newText.isNullOrEmpty()) {
                        viewModel.countryLiveData.value?.filter {
                            it.country.startsWith(newText.orEmpty())
                        } ?: emptyList()
                    } else {
                        viewModel.countryLiveData.value ?: emptyList()
                    }
                    return true
                }
            })
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        if (id == R.id.search) {
//            Toast.makeText(activity, "Settings", Toast.LENGTH_SHORT).show()
//        }
//        return super.onOptionsItemSelected(item)
//    }

    companion object {

        @JvmStatic
        fun newInstance(): CountryListFragment {
            return CountryListFragment()
        }
    }
}