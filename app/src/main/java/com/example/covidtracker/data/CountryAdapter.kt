package com.example.covidtracker.data

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.covidtracker.R


class CountryAdapter(private val countryResult: (String) -> Unit) :
    RecyclerView.Adapter<CountryViewHolder>() {

    var country: List<CountryData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.country_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val item = country[position]
        holder.input(item)
        holder.itemView.setOnClickListener { view ->
            countryResult(item.country)
        }
    }

    override fun getItemCount(): Int {
        return country.size
    }

}