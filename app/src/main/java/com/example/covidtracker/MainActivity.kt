package com.example.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covidtracker.countryList.CountryListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, CountryListFragment())
                .commit()
        }
    }
}