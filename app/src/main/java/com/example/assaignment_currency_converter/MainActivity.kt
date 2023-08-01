package com.example.assaignment_currency_converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.assaignment_currency_converter.api.RetrofitHelper
import com.example.assaignment_currency_converter.api.ServiceApi
import com.example.assaignment_currency_converter.repository.OpenExchangeRepository
import com.example.assaignment_currency_converter.viewModels.MainViewModel
import com.example.assaignment_currency_converter.viewModels.MainViewModelFactory
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val repository = (application as CurrencyConverterApplication).applicationRepository

        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.exchangeRates.observe(this, Observer {
            Log.d("exchangeRates", it.rates.toString())
            var ratesString = it.rates.toString()
            ratesString = ratesString.drop(6)
            ratesString = ratesString.dropLast(1)

            var ratesStringArray : Array<String> = ratesString.split(",").toTypedArray()
            Log.d("list", ratesString.toString())
        })
    }
}