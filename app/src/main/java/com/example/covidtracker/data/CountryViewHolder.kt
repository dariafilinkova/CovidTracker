package com.example.covidtracker.data

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covidtracker.R
import com.squareup.picasso.Picasso

class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val countryName = view.findViewById<TextView>(R.id.countryName)
    val countryCases = view.findViewById<TextView>(R.id.countryCases)
    val countryFlag = view.findViewById<ImageView>(R.id.countryFlag)
//    val numberOfCountry=view.findViewById<ImageView>(R.id.numberOfCountry)


    fun incrementNumberOfCountry(numberOfCountry: Int): Int {
        return numberOfCountry.inc()
    }

    fun input(country: CountryData) {
        countryName.text = country.country
        countryCases.text = country.cases
//        Picasso.whe(countryFlag.context)
//            .load(country.flag)
//            .into(countryFlag)
//        Glide.with(countryFlag.context)
//            .load(country.flag)
//            .into(countryFlag)
        Picasso.get()
            .load(country.flag)
            .into(countryFlag)
    }
}