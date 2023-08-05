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
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteAll()
            repository.getRates("21ec5fc817be452896336e840772e5f3")
        }
        return Result.success()
    }
}