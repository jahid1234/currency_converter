package com.example.assaignment_currency_converter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import  com.example.assaignment_currency_converter.database.CurrencyRate
import androidx.room.Query

@Dao
interface CurrencyRateDao {

    @Insert
    suspend fun addCurrency(currencyRates: CurrencyRate)

    @Query("SELECT * FROM currency_details")
    fun getCurrencyAll() : LiveData<List<CurrencyRate>>

    @Query("SELECT currencyName FROM currency_details")
    fun getCurrencyName() : LiveData<List<String>>
}