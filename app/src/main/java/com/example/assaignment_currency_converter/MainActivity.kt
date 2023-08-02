package com.example.assaignment_currency_converter

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.assaignment_currency_converter.database.CurrencyRate
import com.example.assaignment_currency_converter.databinding.ActivityMainBinding
import com.example.assaignment_currency_converter.viewModels.MainViewModel
import com.example.assaignment_currency_converter.viewModels.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.round

class MainActivity : AppCompatActivity(),CurrencyListAdapter.OnItemClickListenerCustom{

    lateinit var mainViewModel: MainViewModel

    private lateinit var mBinding : ActivityMainBinding
    private lateinit var selectedCurrency : String
    private  var selectedCurrencyRate : String? = "AED"
    private lateinit var currencyArrayList : List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val repository = (application as CurrencyConverterApplication).applicationRepository
        val database  = (application as CurrencyConverterApplication).database

        lifecycleScope.launch {
             database.currencyRateDao().deleteAll()
        }
        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository,database)).get(MainViewModel::class.java)

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
                    mainViewModel.getSingleCurrencyRate(selectedCurrency)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }catch (ex: java.lang.Exception){
            Log.e("Error","Exception: ${ex.localizedMessage}")
        }

        mainViewModel.selectedCurrency.observe(this, Observer {item ->
            item?.let {
                selectedCurrencyRate = item
            }
        })

        mBinding.ediTextAmount.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(s.isEmpty()){
                    mBinding.conversionTextView.text = ""
                }
            }
        })
    }

    override fun onItemClickCustom(currencyDetailsProperty: CurrencyRate) {
        if(mBinding.ediTextAmount.text.isNotEmpty()){
            if(mBinding.ediTextAmount.text.toString().toDouble() > 0) {
                var convertToCurrencyRate = currencyDetailsProperty.currencyRateToDollar
                if (selectedCurrencyRate!!.isNotEmpty() && convertToCurrencyRate.isNotEmpty()) {
                    var conversionRate = String.format("%.2f",(convertToCurrencyRate.toDouble() / selectedCurrencyRate!!.toDouble()) * mBinding.ediTextAmount.text.toString()
                        .toDouble())

                    mBinding.conversionTextView.text = mBinding.ediTextAmount.text.toString() +" "+ selectedCurrency +" = "+ conversionRate.toString() +" "+currencyDetailsProperty.currencyName
                }
            }else{
                Toast.makeText(this, "Give valid number greater than 0", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Give amount greater than 0", Toast.LENGTH_SHORT).show()
        }
    }
}