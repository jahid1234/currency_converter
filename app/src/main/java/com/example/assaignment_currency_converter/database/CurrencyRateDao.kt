package com.example.assaignment_currency_converter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CurrencyRateDao {

    @Insert
    suspend fun addCurrency(quotes: List<CurrencyRate>)

    @Query("SELECT * FROM currency_details")
    suspend fun getCurrencyAll() : List<CurrencyRate>

    @Query("SELECT currencyName FROM currency_details")
    suspend fun getCurrency() : List<CurrencyRate>
}