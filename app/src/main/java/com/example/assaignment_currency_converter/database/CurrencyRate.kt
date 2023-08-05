package com.example.assaignment_currency_converter.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName="currency_details",indices = [Index(value = ["currencyName"], unique = true)])
data class CurrencyRate (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var currencyName : String,
    var currencyRateToDollar : String,
)

