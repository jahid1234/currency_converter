package com.example.assaignment_currency_converter

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.assaignment_currency_converter.api.RetrofitHelper
import com.example.assaignment_currency_converter.api.ServiceApi
import com.example.assaignment_currency_converter.database.CurrencyRate
import com.example.assaignment_currency_converter.databinding.ActivityMainBinding
import com.example.assaignment_currency_converter.repository.OpenExchangeRepository
import com.example.assaignment_currency_converter.viewModels.MainViewModel
import com.example.assaignment_currency_converter.viewModels.MainViewModelFactory
import kotlin.math.log
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel

class MainActivity : AppCompatActivity(),CurrencyListAdapter.OnItemClickListenerCustom{

    lateinit var mainViewModel: MainViewModel

    private lateinit var mBinding : ActivityMainBinding
    private lateinit var selectedCurrency : String
    private lateinit var currencyArrayList : List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val repository = (application as CurrencyConverterApplication).applicationRepository

        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)


        val gridAdapter = CurrencyListAdapter(this)
        mBinding.currencyConvertGrid.adapter = gridAdapter

        //observe all from currency_details table
        mainViewModel.currencyWithRateListData.observe(this, Observer {
             gridAdapter.submitList(it)
        })


        //observe only currency
        mainViewModel.currencyNameListData.observe(this, Observer {
            currencyArrayList= it
            Log.d("Currency", it.toString())
            val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, it)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mBinding.spinner.adapter = adapter
        })

        try {
            mBinding.spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedCurrency = currencyArrayList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }catch (ex: java.lang.Exception){
            Log.e("Error","Exception: ${ex.localizedMessage}")
        }


    }

    override fun onItemClickCustom(currencyDetailsProperty: CurrencyRate) {
        var rate = currencyDetailsProperty.currencyRateToDollar
        Log.d("itemClicked",rate.toString())
    }
}