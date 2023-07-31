package com.example.assaignment_currency_converter.api

import com.example.assaignment_currency_converter.models.CurrencyPojo
import com.example.assaignment_currency_converter.models.OpenExchangePojo
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("latest.json?")
    suspend fun getCurrencyRatesInDollar(@Query("app_id") id: String) : Response<OpenExchangePojo>

    @GET("currencies.json")
    suspend fun getCurrency(): Deferred<CurrencyPojo>

}