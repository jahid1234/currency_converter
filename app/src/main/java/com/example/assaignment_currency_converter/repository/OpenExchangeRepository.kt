package com.example.assaignment_currency_converter.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assaignment_currency_converter.api.ServiceApi
import com.example.assaignment_currency_converter.database.CurrencyDatabase
import com.example.assaignment_currency_converter.database.CurrencyRate
import com.example.assaignment_currency_converter.models.OpenExchangePojo
import com.example.assaignment_currency_converter.utils.NetworkUtils
import kotlinx.coroutines.delay
import java.lang.Exception

class OpenExchangeRepository(
    private val serviceApi: ServiceApi,
    private val database: CurrencyDatabase,
    private val applicationContext: Context
){

    private val ratesLiveData = MutableLiveData<OpenExchangePojo>()
   // private var _selectedCurrency = MutableLiveData<String>()

    var currencyListAndRateData = database.currencyRateDao().getCurrencyAll()
    var currencyNameListData = database.currencyRateDao().getCurrencyName()
    var selectedCurrency  = database.currencyRateDao().getCurrencyRateToDollar("AED")



    val rates: LiveData<OpenExchangePojo>
        get() = ratesLiveData

    suspend fun getRates(apiKey : String){
      //  delay(2000L)
        if(NetworkUtils.isInternetAvailable(applicationContext)){
            try {
                var result = serviceApi.getCurrencyRatesInDollar(apiKey)
                var listResult = result.await()

                var ratesString = listResult.rates.toString()
                ratesString = ratesString.drop(6)
                ratesString = ratesString.dropLast(1)

                var ratesStringArray : Array<String> = ratesString.split(",").toTypedArray()



                for (x in ratesStringArray) {
                    var secondSpitedArray: Array<String> = x.split("=").toTypedArray()
                    for (y in 0..0) {
                        var currencyName = secondSpitedArray[0]
                        var currencyRateToDollar = secondSpitedArray[1]

                        val currencyRate = CurrencyRate(0,currencyName, currencyRateToDollar)
                        database.currencyRateDao().addCurrency(currencyRate)
                    }
                }
            }catch (ex : Exception){
                Log.d("Network Request", ex.message.toString())
                //deleteAll()
               // getRates("bcee75774f2c484f89be9b0cc587bffb")
            }
           // ratesLiveData.value = listResult
        }
    }

    fun getSingleCurrencyRate(currency: String){
        selectedCurrency = database.currencyRateDao().getCurrencyRateToDollar(currency)
    }

    suspend fun deleteAll(){
        delay(5000L)
        database.currencyRateDao().deleteAll()
    }

}