package com.example.covidtracker.location

import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.covidtracker.CovidTrackerFragment
import com.example.covidtracker.R
import com.example.covidtracker.countryList.CountryListFragment
import com.example.covidtracker.databinding.FragmentCountryListBinding
import com.example.covidtracker.databinding.FragmentCovidTrackerBinding
import com.example.covidtracker.databinding.FragmentCovidTrackerLocationBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.jetbrains.annotations.NotNull
import java.util.*


class CovidTrackerLocationFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentCovidTrackerLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCovidTrackerLocationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnChooseCountry).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnGetLocation).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btnGetLocation -> chooseCountryWithCurrentLocation()
            R.id.btnChooseCountry -> findNavController().navigate(R.id.action_covidTrackerLocationFragment_to_country_list)
        }
    }

    fun chooseCountryWithCurrentLocation() {
        if (context?.let { it1 ->
                ContextCompat.checkSelfPermission(
                    it1, android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSION_REQUEST_CODE
            )
        } else {

                getCurrentLocation()
        }
        findNavController().navigate(R.id.action_covidTrackerLocationFragment_to_covid_tracker_home)

    }

    private fun getCurrentLocation() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSION_REQUEST_CODE
        )
        var locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //now getting address from latitude and longitude

        val geocoder = Geocoder(activity, Locale.getDefault())
        var addresses: List<Address>

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "No permission", Toast.LENGTH_SHORT)
            return
        }
        LocationServices.getFusedLocationProviderClient(requireActivity())
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    if (!locationResult.equals(0)) {
                        super.onLocationResult(locationResult)
                    }
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                        .removeLocationUpdates(this)
                    if (!locationResult.equals(0) && locationResult.locations.size > 0) {
                        var locIndex = locationResult.locations.size - 1

                        var latitude = locationResult.locations.get(locIndex).latitude
                        var longitude = locationResult.locations.get(locIndex).longitude

                        addresses = geocoder.getFromLocation(latitude, longitude, 1)
                        var address: String = addresses[0].countryName
                        setCountryResult(address)
                    }
                }
            }, Looper.getMainLooper())

    }

    companion object {

        @JvmStatic
        fun newInstance() = CovidTrackerLocationFragment()
        const val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    private fun setCountryResult(country: String) {
        setFragmentResult("request_key", bundleOf(("country" to country)))
    }
}