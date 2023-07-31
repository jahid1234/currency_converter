package com.example.assaignment_currency_converter.models

data class OpenExchangePojo(
    val base: String,
    val disclaimer: String,
    val license: String,
    val rates: Rates,
    val timestamp: Int
)