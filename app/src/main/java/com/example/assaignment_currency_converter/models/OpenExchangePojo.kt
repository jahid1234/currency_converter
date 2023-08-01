package com.example.assaignment_currency_converter.models

import com.google.gson.annotations.SerializedName

data class OpenExchangePojo(
   /* val base: String,
    val disclaimer: String,
    val license: String,
    val timestamp: Int,
    var rates : Rates?  = Rates()*/

    @SerializedName("disclaimer" ) var disclaimer : String? = null,
    @SerializedName("license"    ) var license    : String? = null,
    @SerializedName("timestamp"  ) var timestamp  : Int?    = null,
    @SerializedName("base"       ) var base       : String? = null,
    @SerializedName("rates"      ) var rates      : Rates?  = Rates()
)