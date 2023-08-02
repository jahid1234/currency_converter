package com.example.assaignment_currency_converter.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assaignment_currency_converter.api.ServiceApi
import com.example.assaignment_currency_converter.database.CurrencyDatabase
import com.example.assaignment_currency_converter.database.CurrencyRate
import com.example.assaignment_currency_converter.models.OpenExchangePojo
import com.example.assaignment_currency_converter.utils.NetworkUtils

class OpenExchangeRepository(
    private val serviceApi: ServiceApi,
    private val database: CurrencyDatabase,
    private val applicationContext: Context
){

    private val ratesLiveData = MutableLiveData<OpenExchangePojo>()

    var currencyListAndRateData = database.currencyRateDao().getCurrencyAll()
    var currencyNameListData = database.currencyRateDao().getCurrencyName()



    val rates: LiveData<OpenExchangePojo>
        get() = ratesLiveData

    suspend fun getRates(apiKey : String){

        if(NetworkUtils.isInternetAvailable(applicationContext)){
            var result = serviceApi.getCurrencyRatesInDollar(apiKey)
            var listResult = result.await()

            var ratesString = listResult.rates.toString()
            ratesString = ratesString.drop(6)
            ratesString = ratesString.dropLast(1)

            var ratesStringArray : Array<String> = ratesString.split(",").toTypedArray()

            for (x in ratesStringArray) {
                var secondSpitedArray : Array<String> = x.split("=").toTypedArray()
                for(y  in 0..0){
                    var currencyName = secondSpitedArray[0]
                    var currencyRateToDollar = secondSpitedArray[1]

                    val currencyRate = CurrencyRate(0,currencyName,currencyRateToDollar)
                    database.currencyRateDao().addCurrency(currencyRate)
                }
            }
            ratesLiveData.value = listResult
        }else{ }
    }
}