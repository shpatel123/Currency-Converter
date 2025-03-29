package com.example.currencyconverter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CurrencyAdapter(private val context: Context, private val currencyList: List<SecondActivity.Currency>) : BaseAdapter() {

    override fun getCount(): Int = currencyList.size
    override fun getItem(position: Int): Any = currencyList[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        val flagImage = view.findViewById<ImageView>(R.id.flagImage)
        val countryName = view.findViewById<TextView>(R.id.countryName)

        val currency = currencyList[position]
        flagImage.setImageResource(currency.flag)
        countryName.text = currency.name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }
}
