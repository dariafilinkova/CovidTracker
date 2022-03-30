package com.example.covidtracker

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.covidtracker.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        navController = findNavController(R.id.main_fragment)
//        setupActionBarWithNavController(navController)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigation


//        if (savedInstanceState == null) {
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.main_fragment, CovidTrackerFragment.newInstance())
//                .commit()
//        }
        //val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
       navView.setupWithNavController(navController)
//        bottomNavigation.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.home -> {
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.fragmentContainer, CovidTrackerFragment.newInstance())
//                        .addToBackStack(null)
//                        .commit()
//                }
//                R.id.notification -> {
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(
//                            R.id.fragmentContainer,
//                            CovidTrackerNotificationFragment.newInstance()
//                        )
//                        .addToBackStack(null)
//                        .commit()
//                }
//            }
//            true
//        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }
}