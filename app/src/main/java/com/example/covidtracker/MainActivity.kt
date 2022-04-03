package com.example.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.covidtracker.databinding.ActivityMainBinding
import com.example.covidtracker.location.CovidTrackerLocationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //var lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CovidTracker)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigation
        var navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.covid_tracker_home, R.id.covid_tracker_location, R.id.covid_tracker_notification))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

    }

    fun returnToHomeFragment() {
        binding.bottomNavigation.selectedItemId = R.id.covid_tracker_home
    }

}