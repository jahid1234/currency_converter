package com.example.assaignment_currency_converter.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.assaignment_currency_converter.database.CurrencyDatabase
import com.example.assaignment_currency_converter.database.CurrencyRate
import com.example.assaignment_currency_converter.models.OpenExchangePojo
import com.example.assaignment_currency_converter.repository.OpenExchangeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch



class MainViewModel(private val repository: OpenExchangeRepository) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    val currencyWithRateListData : LiveData<List<CurrencyRate>> = repository.currencyListAndRateData
    val currencyNameListData : LiveData<List<String>> = repository.currencyNameListData
    val selectedCurrencyRateFromViewModel : LiveData<String> = repository.selectedCurrencyRate

    val api_status = repository.apiStatus

    init {
        getRates("21ec5fc817be452896336e840772e5f3")
    }

     fun getRates(apiKey : String){
        coroutineScope.launch {
            try{
                repository.deleteAll()
                repository.getRates(apiKey)
            }catch (t : Throwable){
                Log.e("error",t.message.toString() )
            }
        }
    }

     fun getSingleCurrencyRate(currency : String){
         repository.getSingleCurrencyRate(currency)
    }


}