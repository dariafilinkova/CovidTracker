package com.example.covidtracker.location

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.translationMatrix
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.covidtracker.CovidTrackerFragment
import com.example.covidtracker.R
import com.example.covidtracker.countryList.CountryListFragment
import com.example.covidtracker.databinding.FragmentCovidTrackerLocationBinding
import com.google.android.gms.location.*
import org.jetbrains.annotations.NotNull
import java.security.Permission
import java.util.*



class CovidTrackerLocationFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentCovidTrackerLocationBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerPermissionListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCovidTrackerLocationBinding.inflate(inflater, container, false)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

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
            R.id.btnChooseCountry -> findNavController().navigate(R.id.action_covid_tracker_location_fragment_to_country_list_fragment)
        }
    }


    private fun registerPermissionListener() {
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    Log.i("DEBUG", "permission granted")
                } else {
                    Log.i("DEBUG", "permission denied")
                }
            }
    }


    private fun chooseCountryWithCurrentLocation() {
        if (checkPermissions()) {
            getCurrentLocation()
            findNavController().navigate(R.id.action_covid_tracker_location_fragment_to_covid_tracker_fragment)
        } else {
            permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getCurrentLocation() {
        val locationManager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        ) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    requireContext(),
                    "You need to allow use your location",
                    Toast.LENGTH_SHORT
                ).show()
            }
            fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
//                val location: Location? = task.result
//                if (location != null) {
//                    Toast.makeText(requireContext(), "Get success", Toast.LENGTH_SHORT).show()
//                    Log.d("aaa", "${location.latitude}")
//                    Log.d("aaa", "${location.longitude}")
//
//                } else {
                val locationRequest = LocationRequest.create().apply {
                    interval = 10000
                    fastestInterval = 1000
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    maxWaitTime = 1000
                    numUpdates = 2
                }
                val geocoder = Geocoder(activity, Locale.getDefault())
                var addresses: List<Address>
                LocationServices.getFusedLocationProviderClient(requireActivity())
                    .requestLocationUpdates(locationRequest, object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            if (!locationResult.equals(0)) {
                                super.onLocationResult(locationResult)
                            }
                            LocationServices.getFusedLocationProviderClient(requireActivity())
                                .removeLocationUpdates(this)
                            if (!locationResult.equals(0) && locationResult.locations.size > 0) {
                                val locIndex = locationResult.locations.size - 1

                                val latitude = locationResult.locations[locIndex].latitude
                                val longitude = locationResult.locations[locIndex].longitude

                                addresses = geocoder.getFromLocation(latitude, longitude, 1)
                                    //val address: String = addresses[0].countryName
                                val address: String = addresses.first().countryName.toString()
                                    //  val locale : Locale("en":,add)
                                Log.d("c", address)
                                setCountryResult(address)
                            }
                        }
                    }, Looper.getMainLooper())
            }

        }
//        }
//        else {
//            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
//        }
    }



    private fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }



    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    companion object {

        @JvmStatic
        fun newInstance() = CovidTrackerLocationFragment()
        const val REQUEST_PERMISSION_CODE = 100
    }

    private fun setCountryResult(country: String) {
        setFragmentResult("request_key", bundleOf(("country" to country)))
    }
}