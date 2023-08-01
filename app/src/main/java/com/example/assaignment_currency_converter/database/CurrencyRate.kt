package com.example.assaignment_currency_converter.database

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName="currency_details")
class CurrencyRate (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var currencyName : String,
    var currencyRateToDollar : String
)

