package com.example.assaignment_currency_converter.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.assaignment_currency_converter.api.ServiceApi
import com.example.assaignment_currency_converter.database.CurrencyDatabase
import com.example.assaignment_currency_converter.database.CurrencyRate
import com.example.assaignment_currency_converter.models.OpenExchangePojo
import com.example.assaignment_currency_converter.utils.NetworkUtils
import kotlinx.coroutines.delay
import java.lang.Exception

enum class ApiStatus{LOADING, DONE,ERROR}

class OpenExchangeRepository(
    private val serviceApi: ServiceApi,
    private val database: CurrencyDatabase,
    private val applicationContext: Context
){

    private val _apiStatus = MutableLiveData<ApiStatus>()

    val apiStatus : LiveData<ApiStatus>
        get() = _apiStatus

    private val ratesLiveData = MutableLiveData<OpenExchangePojo>()

    var currencyListAndRateData = database.currencyRateDao().getCurrencyAll()
    var currencyNameListData = database.currencyRateDao().getCurrencyName()


    var _selectedCurrencyRate  = MediatorLiveData<String>()

    val selectedCurrencyRate : LiveData<String>
            get() = _selectedCurrencyRate

    val rates: LiveData<OpenExchangePojo>
        get() = ratesLiveData

    suspend fun getRates(apiKey : String){
        if(NetworkUtils.isInternetAvailable(applicationContext)){
            try {
                Log.d("Insert", "insert job started")
                var result = serviceApi.getCurrencyRatesInDollar(apiKey)

                var listResult = result.await()

                var ratesString = listResult.rates.toString()
                ratesString = ratesString.drop(6)
                ratesString = ratesString.dropLast(1)

                var ratesStringArray : Array<String> = ratesString.split(",").toTypedArray()

                delay(1000L)

                for (x in ratesStringArray) {
                    var secondSpitedArray: Array<String> = x.split("=").toTypedArray()
                    for (y in 0..0) {
                        var currencyName = secondSpitedArray[0]
                        var currencyRateToDollar = secondSpitedArray[1]

                        val currencyRate = CurrencyRate(0,currencyName, currencyRateToDollar)
                        database.currencyRateDao().addCurrency(currencyRate)
                    }
                }
                _apiStatus.postValue(ApiStatus.DONE)
                Log.d("Insert", "insert job done")
            }catch (ex : Exception){
                _apiStatus.postValue(ApiStatus.ERROR)
                Log.d("Network_Request", ex.message.toString())
                //deleteAll()
               // getRates("bcee75774f2c484f89be9b0cc587bffb")
            }
        }
    }

    fun getSingleCurrencyRate(currency: String){
        _selectedCurrencyRate.addSource(database.currencyRateDao().getCurrencyRateToDollar(currency)) {
            _selectedCurrencyRate.value = it
        }
    }

    suspend fun deleteAll(){
        _apiStatus.postValue(ApiStatus.LOADING)
        Log.d("Delete", "delete job started")
        database.currencyRateDao().deleteAll()
        delay(5000L)
        Log.d("Delete", "delete job completed")
    }

}