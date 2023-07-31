package com.example.assaignment_currency_converter.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assaignment_currency_converter.api.ServiceApi
import com.example.assaignment_currency_converter.models.OpenExchangePojo

class OpenExchangeRepository (private val serviceApi: ServiceApi){

    private val ratesLiveData = MutableLiveData<OpenExchangePojo>()

    val rates: LiveData<OpenExchangePojo>
        get() = ratesLiveData

    suspend fun getRates(apiKey : String){
        val result = serviceApi.getCurrencyRatesInDollar(apiKey)

        if(result?.body() != null){
            ratesLiveData.postValue(result.body())
        }
    }
}