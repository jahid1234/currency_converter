package com.example.assaignment_currency_converter.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.assaignment_currency_converter.CurrencyConverterApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyDetailsDownloadWorker(private val context: Context,params : WorkerParameters) :
    Worker(context,params) {
    override fun doWork(): Result {
        Log.d("WokerManager", "worker called")
        val repository = (context as CurrencyConverterApplication).applicationRepository
        val database = context.database
        CoroutineScope(Dispatchers.IO).launch {
            database.currencyRateDao().deleteAll()
            repository.getRates("bcee75774f2c484f89be9b0cc587bffb")
        }
        return Result.success()
    }
}