package com.example.assaignment_currency_converter

import android.app.Application
import com.example.assaignment_currency_converter.api.RetrofitHelper
import com.example.assaignment_currency_converter.api.ServiceApi
import com.example.assaignment_currency_converter.database.CurrencyDatabase
import com.example.assaignment_currency_converter.repository.OpenExchangeRepository

class CurrencyConverterApplication : Application() {

    lateinit var applicationRepository: OpenExchangeRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val quoteService = RetrofitHelper.getInstance().create(ServiceApi::class.java)
        val database = CurrencyDatabase.getDatabase(applicationContext)
        applicationRepository = OpenExchangeRepository(quoteService, database, applicationContext)
    }
}