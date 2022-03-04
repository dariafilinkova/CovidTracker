package com.example.covidtracker.data

import android.content.Context
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
    val numberOfCountry=view.findViewById<TextView>(R.id.numberOfCountry)

    fun input(country: CountryData) {
        countryName.text = country.country
        countryCases.text = country.cases
        for(i in 0..listOf<CountryData>().size) {
            numberOfCountry.text = adapterPosition.inc().toString()
        }
        Picasso.get()
            .load(country.countryInfo?.flag)
            .into(countryFlag)
    }
}