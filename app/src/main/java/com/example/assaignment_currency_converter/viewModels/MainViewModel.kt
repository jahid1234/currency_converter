package com.example.assaignment_currency_converter.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.assaignment_currency_converter.models.OpenExchangePojo
import com.example.assaignment_currency_converter.repository.OpenExchangeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val repository: OpenExchangeRepository) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val exchangeRates: LiveData<OpenExchangePojo>
        get() = repository.rates


    init {
        getRates("bcee75774f2c484f89be9b0cc587bffb")
    }

    private fun getRates(apiKey : String){
        coroutineScope.launch {
            try{
                repository.getRates(apiKey)
            }catch (t : Throwable){
                Log.e("error",t.message.toString() )
            }
        }
    }
}